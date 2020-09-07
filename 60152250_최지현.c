#include <stdio.h>
#include <stdlib.h>
#include <openssl/rsa.h>
#include <openssl/evp.h>
#include <openssl/pem.h>
#define CHUNK_SIZE 1024
int main(void) {
	printf(">> Beginning of the 1st part of Alice\n\n");
    /*
     *  Generate EC domain params
     */
    EVP_PKEY_CTX *pctx = EVP_PKEY_CTX_new_id(EVP_PKEY_EC, NULL);
    if (pctx == NULL) {        goto cleanup;    }
    EVP_PKEY_paramgen_init(pctx);
    EVP_PKEY_CTX_set_ec_paramgen_curve_nid(pctx, NID_X9_62_prime256v1);
    EVP_PKEY *params = NULL;
    EVP_PKEY_paramgen(pctx, &params);
    if (params == NULL) {       goto cleanup;    }

    EVP_PKEY_CTX_free(pctx);

    /*
     *  Generate Alice's EC key pair
     */
    EVP_PKEY_CTX *kctx = EVP_PKEY_CTX_new(params, NULL);
    if (kctx == NULL) {        goto cleanup;    }

    EVP_PKEY_keygen_init(kctx);
    EVP_PKEY *alice_eckeypair = EVP_PKEY_new();
    EVP_PKEY_keygen(kctx, &alice_eckeypair);
    if (alice_eckeypair == NULL) {        goto cleanup;    }
    EVP_PKEY_CTX_free(kctx);

        /*
     * serialize Alice’s public
     */
    EC_KEY *alice_pub_eckey = EVP_PKEY_get1_EC_KEY(alice_eckeypair);
    EC_POINT *alice_pub_ecpoint = EC_KEY_get0_public_key(alice_pub_eckey);
    if(alice_pub_ecpoint == NULL){     goto cleanup;	}
    size_t alice_bufLen = EC_POINT_point2oct(EC_KEY_get0_group(alice_pub_eckey),
            alice_pub_ecpoint, POINT_CONVERSION_COMPRESSED, NULL, 0, NULL);

    unsigned char *alice_pub_char = malloc(alice_bufLen);
    int result = EC_POINT_point2oct(EC_KEY_get0_group(alice_pub_eckey), alice_pub_ecpoint,
            POINT_CONVERSION_COMPRESSED, alice_pub_char, alice_bufLen, NULL);
    if(!result){	goto cleanup;	}

    // file open
    FILE *afp = fopen("alice_public.key", "wb");
    if(afp == NULL){		goto cleanup;		}
    printf("[Alice's public key]\n");
    for(int i = 0; i < alice_bufLen; i++){
    	printf(" 0x%X", alice_pub_char[i]);
    }
    printf("\n");
    fwrite(alice_pub_char, sizeof(alice_pub_char), alice_bufLen, afp);
    fclose(afp);
    printf("\nalice_public.key file created\n");
    printf("<< End of the 1st part of Alice\n\n");



    printf(">> Beginning of the 1st part of Bob\n\n");
    /*
    *  Generate Bob's EC key pair
    */
    kctx = EVP_PKEY_CTX_new(params, NULL);
    if (kctx == NULL) {        goto cleanup;    }
    EVP_PKEY_keygen_init(kctx);
    EVP_PKEY *bob_eckeypair = EVP_PKEY_new();
    EVP_PKEY_keygen(kctx, &bob_eckeypair);
    if (bob_eckeypair == NULL) {     goto cleanup;    }

    //ec the peer'public k serialization

    EC_KEY *bob_eckey = EVP_PKEY_get1_EC_KEY(bob_eckeypair);
    EC_POINT *bob_pub_ecpoint = EC_KEY_get0_public_key(bob_eckey);
    if (bob_pub_ecpoint == -1) {        goto cleanup;    }
    size_t bufLen = EC_POINT_point2oct(EC_KEY_get0_group(bob_eckey),
          bob_pub_ecpoint, POINT_CONVERSION_COMPRESSED, NULL, 0, NULL);

    unsigned char *bob_pub_char = malloc(bufLen);
    result = EC_POINT_point2oct(EC_KEY_get0_group(bob_eckey), bob_pub_ecpoint,
            POINT_CONVERSION_COMPRESSED, bob_pub_char, bufLen, NULL);
    if (!result)        goto cleanup;

    printf("[Bob's public key]\n");
    for(int i = 0; i < bufLen; i++){
    	printf(" 0x%X", bob_pub_char[i]);
    }
    printf("\n\n");

    // file open
    FILE *bfp = fopen("bob_public.key", "wb");
    if(bfp == NULL){		goto cleanup;		}
    fwrite(bob_pub_char, sizeof(bob_pub_char), bufLen, bfp);
    fclose(bfp);
    printf("\nbob_public.key file created\n\n");

    printf("<< End of the 1st part of Bob\n");

//____________________________________________________________________________
    printf(">> Beginning of the 2nd part of Alice");

    /*
    * file read by Alice. print out in hexadecimal format.
    */
    printf("[Data from bob_public.key]\n");

    FILE *bkeyfp = fopen("bob_public.key", "rb");
    if (bkeyfp == NULL){ 	goto cleanup;    	}
    alice_pub_char = malloc(CHUNK_SIZE);
    int inlen = 0;
    inlen = fread(alice_pub_char, sizeof(char), CHUNK_SIZE, bkeyfp);
    if(inlen < 1)	goto cleanup;
    for(int i = 0 ; i<inlen; i++){
    	printf("0x%X ", alice_pub_char[i]);
    }
    fclose(bkeyfp);
    printf("\n\n");

    //de-serialization Bob's public key (done by Alice) : char [] => EVP_PKEY
    EC_KEY *bob_reckey = EC_KEY_new_by_curve_name(NID_X9_62_prime256v1);
    if (bob_reckey == -1)        goto cleanup;

    EC_POINT *bob_recpoint = EC_POINT_new(EC_KEY_get0_group(bob_reckey));
    if (bob_recpoint == -1)        goto cleanup;

    result = EC_POINT_oct2point(EC_KEY_get0_group(bob_reckey), bob_recpoint, bob_pub_char, bufLen, NULL);
    if (result == 0) goto cleanup;

    EC_KEY_set_public_key(bob_reckey, bob_recpoint);

    EVP_PKEY *bob_pub_key = EVP_PKEY_new();
    EVP_PKEY_set1_EC_KEY(bob_pub_key, bob_reckey);


    printf("[Shared secret on Alice]\n\n");
    /*
    * Compute ECDH shared secret. (done by Alice)
     */
    EVP_PKEY_CTX *ctx = EVP_PKEY_CTX_new(alice_eckeypair, NULL);
    EVP_PKEY_derive_init(ctx);
    EVP_PKEY_derive_set_peer(ctx, bob_pub_key);
    size_t ecdh_key_len = 0;

   // check buffer len(address save)
    EVP_PKEY_derive(ctx, NULL, &ecdh_key_len);
    unsigned char *ecdh_key_on_alice = malloc (ecdh_key_len);
    if(ecdh_key_on_alice == NULL){     goto cleanup;    }
    EVP_PKEY_derive(ctx, ecdh_key_on_alice, &ecdh_key_len);
    for(int i = 0; i < ecdh_key_len; i++){
    	printf("0x%X ", ecdh_key_on_alice[i]);
    }
    printf("\n\n");
    printf("<< End of the 2nd part of Alice\n\n");

//---------------------------------------------------------------------------

    printf(">> Beginning of the 2nd part of Bob");
    printf("[Data from bob_public.key]\n");
    /*
    * file read by Bob. print out in hexadecimal format.
    */
    FILE *akeyfp = fopen("alice_public.key", "rb");
    if (akeyfp == NULL){ 	goto cleanup;    	}
    unsigned char* inbuf = malloc(CHUNK_SIZE);
    inlen = 0;
    inlen = fread(inbuf, sizeof(char), CHUNK_SIZE, akeyfp);
    if(inlen < 1)	goto cleanup;
    for(int i = 0; i < inlen; i++){
    	printf("0x%X ", inbuf[i]);
    }
    printf("\n\n");
    fclose(akeyfp);

    /*
     * de-serialization
     */
    printf("[Shared secret on Bob]\n");
    EC_KEY *alice_reckey = EC_KEY_new_by_curve_name(NID_X9_62_prime256v1);
    if (alice_reckey == -1)
        goto cleanup;

    EC_POINT *alice_recpoint = EC_POINT_new(EC_KEY_get0_group(alice_reckey));
    if (alice_recpoint == -1)
        goto cleanup;

    result = EC_POINT_oct2point(EC_KEY_get0_group(alice_reckey), alice_recpoint,
            alice_pub_char, bufLen, NULL);
    if (result == 0) goto cleanup;

    EC_KEY_set_public_key(alice_reckey, alice_recpoint);

    EVP_PKEY *alice_pub_key = EVP_PKEY_new();
    EVP_PKEY_set1_EC_KEY(alice_pub_key, alice_reckey);


    /*
    * Compute ECDH shared secret. (done by Bob)
    */
    EVP_PKEY_CTX *bobctx = EVP_PKEY_CTX_new(alice_eckeypair, NULL);

    result = EVP_PKEY_derive_init(bobctx);
    if(result != 1){    	goto cleanup;    }
    result = EVP_PKEY_derive_set_peer(bobctx, alice_pub_key);
    if(result != 1){    	goto cleanup;    }

    size_t bob_ecdh_key_len = 0;
    EVP_PKEY_derive(ctx, NULL, &bob_ecdh_key_len);
    unsigned char *ecdh_key_on_bob = malloc (bob_ecdh_key_len);
    if(ecdh_key_on_bob == NULL){    	goto cleanup;    }
    EVP_PKEY_derive(ctx, ecdh_key_on_bob, &bob_ecdh_key_len);

    printf("bob length ecdh key: %d\n",bob_ecdh_key_len);
    for(int i = 0; i <bob_ecdh_key_len; i++){
    	printf("0x%X ", ecdh_key_on_bob[i]);
    }
    printf("\n\n");

   	/*
   	 * compare ECDH shared secret keys.
   	 */
    printf("[Comparison result]\n");
    int comp = memcmp(ecdh_key_on_bob, ecdh_key_on_alice, bob_ecdh_key_len);
    if(comp == 0 )
    	printf("Equal\n");
    else
    	printf("Different\n");

cleanup:
    if (pctx != NULL)EVP_PKEY_CTX_free(pctx);
    if (params != NULL)EVP_PKEY_free(params);
    if (alice_eckeypair != NULL)EVP_PKEY_free(alice_eckeypair);
    if (bob_eckeypair != NULL)EVP_PKEY_free(bob_eckeypair);
    if (bob_reckey != NULL)EC_KEY_free(bob_reckey);
    if (bob_recpoint != NULL)EC_POINT_free(bob_recpoint);
    if (ecdh_key_on_alice != NULL)EC_POINT_free(ecdh_key_on_alice);
    if (alice_reckey != NULL)EC_KEY_free(alice_reckey);
    if (alice_recpoint != NULL)EC_POINT_free(alice_recpoint);
    if (ecdh_key_on_bob != NULL)EC_POINT_free(ecdh_key_on_bob);
    if (ctx != NULL)EVP_PKEY_CTX_free(ctx);
    if (bobctx != NULL)EVP_PKEY_CTX_free(bobctx);
    return EXIT_SUCCESS;
}


/*
 ============================================================================
 Name        : rsa.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <openssl/rsa.h>
#include <openssl/pem.h>
#include <openssl/evp.h>
int main(void) {
	puts("!!!Hello World!!!"); /* prints !!!Hello World!!! */


	RSA *bob_keypair = RSA_generate_key(2048, 3, NULL, NULL);
	if(bob_keypair == NULL)	goto cleanup;

	//Serialize bob's rsa public key (do)
	BIO  *pub = BIO_new(BIO_s_mem());
	if(pub == NULL)	goto cleanup;


	PEM_write_bio_RSAPublicKey(pub, bob_keypair);

	size_t pub_len = BIO_pending(pub);
	char *pub_key = malloc(pub_len+1);
	if(pub_key== NULL)	goto cleanup;

	BIO_read(pub, pub_key, pub_len);
	pub_key[pub_len] = '\0';
	printf("%s\n", pub_key);

	/*
	 * Serialize Bob's RSA private key : struct RSA => char[]
	 */
	BIO *pri = BIO_new(BIO_s_mem());
	if(pri == NULL)	goto cleanup;

	PEM_write_bio_RSAPrivateKey(pri, bob_keypair, NULL, NULL, 0, NULL, NULL);

	size_t pri_len = BIO_pending(pri);
	char *pri_key = malloc(pri_len+1);
	if(pri_key == NULL)	goto cleanup;
	BIO_read(pri, pri_key, pri_len);
	pri_key[pri_len] = '\0';
	printf("%s\n", pri_key);


	/*
	 * De-serialize Bob's public key ( done by Alice ) : char [] => struct RSA
	 */
	BIO *rpub = BIO_new_mem_buf(pub_key, -1);
	BIO_write(rpub, pub_key, pub_len);

	RSA *bob_rsa_pubkey = NULL;
	PEM_read_bio_RSAPublicKey(rpub, &bob_rsa_pubkey, NULL, NULL);


	/*
	 * Encrypt M(Msg) with bob's public key ( done by Alice )
	 */
	char *msg = "hello!!";
	unsigned char *ctxt = malloc(RSA_size(bob_rsa_pubkey));
	int ctxt_len = RSA_public_encrypt(strlen(msg) +1, msg, ctxt,
			bob_rsa_pubkey, RSA_PKCS1_OAEP_PADDING);
	if(ctxt_len == -1)	goto cleanup;
	for(int i = 0; i < ctxt_len; i++){
		printf("0x%X", ctxt[i]);
	}



	/*
	 *
	BIO *pri = BIO_new(BIO_s_mem());
	PEM_write_bio_RSAPrivateKey(pri, bob_keypair, NULL, NULL, 0, NULL, NULL);
	size_t pri_len = BIO_pending(pri);
	char *pri_key = malloc(pri_len + 1);
	BIO_read(pri, pri_key, pri_len);
	size_t pri_len = BIO_pending(pri);
	char *pri_key = malloc(pri_len + 1);
	BIO_read(pri, pri_key, pri_len);
	pri_key[pri_len] = '\0';
	*/
	unsigned char *decrypt = malloc(RSA_size(bob_keypair));
	if(RSA_private_decrypt(ctxt_len, (unsigned char*)ctxt,
			decrypt, bob_keypair, RSA_PKCS1_OAEP_PADDING == -1)){
		//ERROR
	}

	BIO *rpri = BIO_new_mem_buf(pri_key, -1);
	int result = BIO_write(rpri, pri_key, pri_len);

	RSA *rsa_prikey = NULL;
	if(!PEM_read_bio_RSAPrivateKey(rpri, &rsa_prikey, NULL, NULL)){
		printf("PEM_Read \n");
		goto cleanup;
	}


	decrypt = malloc(RSA_size(rsa_prikey));

	int ptxt_len = RSA_private_decrypt(ctxt_len, (unsigned char*)ctxt, decrypt,
			rsa_prikey, RSA_PKCS1_OAEP_PADDING);
	if(ptxt_len == -1)	goto cleanup;

	printf("\n");

	for(int i = 0; i < ptxt_len; i++){
			printf("\s", decrypt[i]);
	}

cleanup :
	if(bob_keypair != NULL)	RSA_free(bob_keypair);
	if(pub != NULL)	BIO_free_all(pub);
	if(pub_key != NULL)	free(pub_key);
	if(pri != NULL)	BIO_free_all(pri);
	if(pri_key != NULL)	free(pri_key);
	if(ctxt != NULL)	free(ctxt);
	if(bob_rsa_pubkey != NULL)	RSA_free(bob_rsa_pubkey);
	if(rpub =! NULL)	BIO_free_all(rpub);

	return EXIT_SUCCESS;

}


/*
 ============================================================================
 Name        : ecdh.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <openssl/evp.h>
#include <openssl/ec.h>

int main(void) {
	puts("!!!Hello World!!!"); /* prints !!!Hello World!!! */

	/*
	 * Generate EC domain parameters.
	 */
	EVP_PKEY_CTX *pctx = EVP_PKEY_CTX_new_id(EVP_PKEY_EC, NULL);
	if(pctx == NULL)	goto cleanup;
	EVP_PKEY_paramgen_init(pctx);

	// SET EC's TYPE
	EVP_PKEY_CTX_set_ec_paramgen_curve_nid(pctx, NID_X9_62_prime256v1);

	EVP_PKEY *params = NULL;
	EVP_PKEY_paramgen(pctx, &params);

	/*
	 * Generate Alice's EC key pair (done by Alice) => EVP_PKEY
	 */
	EVP_PKEY_CTX *kctx = EVP_PKEY_CTX_new(params, NULL);

	EVP_PKEY *pkeypair = EVP_PKEY_new();
	EVP_PKEY_keygen_init(kctx);
	EVP_PKEY_keygen_init(kctx, &pkeypair);

	/*
	 * Generate Bob's EC key pair (done by Bob) => EVP_PKEY
	 */
	EVP_PKEY_CTX *kctx = EVP_PKEY_CTX_new(params, NULL);

	EVP_PKEY *peerkeypair = EVP_PKEY_new();
	EVP_PKEY_keygen_init(kctx);
	EVP_PKEY_keygen_init(kctx, &peerkeypair);

	/*
	 * Serialize Bob's public key (done by Bob) : EVP_PKEY => char []
	 */
	EC_KEY *peer_eckey = EVP_PKEY_get1_EC_KEY(peerkeypair);
	EC_POINT *peer_pub_ecpoint = EC_KEY_get0_public_key(peer_eckey);

	/*size_t bufLen = EC_POINT_point3oct(EC_KEY_get0_group(peer_eckey),
			peer_pub_ecpoint, POINT_CONVERSION_COMPRESSED, NULL, 0, NULL);
	unsigned char *peerPubkeyChar = (unsigned char *)malloc(bufLen);
	*/
	// BUFFER LEN CHECK
	int bufLen = EC_POINT_point2oct(EC_KEY_get0_group(peer_eckey),
			peer_pub_ecpoint, POINT_CONVERSION_COMPRESSED, NULL, 0, NULL);
	unsigned char *peerPubkeyChar = (unsigned char *)malloc(bufLen);

	EC_POINT_point2oct(EC_KEY_get0_group(peer_eckey),
				peer_pub_ecpoint, POINT_CONVERSION_COMPRESSED, peerPubkeyChar, 0, NULL);
	// Bob SEND to Alice

	/*
	 * De-Serialize Bob's public key (done by Alice) : char [] => EVP_PKEY
	 */
	EC_KEY *reckey = EC_KEY_new_by_curve_name(NID_X9_62_prime256v1);
	EC_POINT *rpoint = EC_POINT_new(EC_KEY_get0_group(reckey));

	EC_POINT_oct2point(EC_KEY_get0_group(reckey), rpoint, peerPubkeyChar,
			bufLen, NULL);
	EC_KEY_set_public_key(reckey, rpoint);

	EVP_PKEY *peer_pub_evp = EVP_PKEY_new();
	EVP_PKEY_set1_EC_KEY(peer_pub_evp, reckey);

	/*
	 * Compute ECDH shared secret. (done by Alice)
	 */

	EVP_PKEY_CTX *ctx = EVP_PKEY_CTX_new(pkeypair, NULL);
	EVP_PKEY_derive_init(ctx);
	EVP_PKEY_derive_set_peer(ctx, peer_pub_evp);

	size_t secret_len = 0;
	// check buffer len(address save)
	EVP_PKEY_derive(ctx, NULL, &secret_len);
	unsigned char *secret = (unsigned char *)malloc(secret_len);

	// save secret buffer
	EVP_PKEY_derive(ctx, secret, &secret_len);

	/*
	 * Serialize Alice's public key (done by Alice) : EVP_PKEY => char []
	 */

	/*
	 * De-Serialize Alice's public key (done by Bob) : char [] => EVP_PKEY
	 */

	/*
	 * Compute ECDH shared secret. (done by Bob)
	 */

	/*
	 * compare ECDH shared secret keys.
	 */
cleanup:
	return EXIT_SUCCESS;
}


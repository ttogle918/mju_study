/*
 ============================================================================
 Name        : hybrid_crypto_0828.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <openssl/evp.h>
#include <openssl/rsa.h>

int main(void) {
	/**
	 * Generate Bob's RSA key pair (struct EVP_PKEY)
	 */
	// allocate and configure context for key pair generation
	EVP_PKEY_CTX *keyCtx = EVP_PKEY_CTX_new_id(EVP_PKEY_RSA, NULL);
	if (keyCtx == NULL) goto cleanup;
	EVP_PKEY_keygen_init(keyCtx);
	EVP_PKEY_CTX_set_rsa_keygen_bits(keyCtx, 2048);

	EVP_PKEY *bob_keypair = NULL;
	EVP_PKEY_keygen(keyCtx, &bob_keypair);
	if (bob_keypair == NULL) goto cleanup;
	EVP_PKEY_CTX_free(keyCtx);

	/**
	 * Serialize Bob's public key (done by Bob): EVP_PKEY => char []
	 */
	BIO *publicBIO = BIO_new(BIO_s_mem());
	if (publicBIO == NULL) goto cleanup;
	PEM_write_bio_PUBKEY(publicBIO, bob_keypair);
	int publicKeyLen = BIO_pending(publicBIO);
	unsigned char *pubkeyChar = malloc(publicKeyLen+1);
	BIO_read(publicBIO, pubkeyChar, publicKeyLen);
	pubkeyChar[publicKeyLen] = '\0';

	printf("%s\n", pubkeyChar);

	/**
	 * De-serialize Bob's public key (done by Alice): char [] => EVP_PKEY
	 */
	unsigned char *rsaPublicKeyChar = pubkeyChar;
	BIO *rsaPublicBIO = BIO_new_mem_buf(rsaPublicKeyChar, -1);
	RSA *rsaPublicKey = NULL;
	PEM_read_bio_RSA_PUBKEY(rsaPublicBIO, &rsaPublicKey, NULL, NULL);

	EVP_PKEY *publicKey = EVP_PKEY_new();
	EVP_PKEY_assign_RSA(publicKey, rsaPublicKey);


	/**
	 * EVP_SealInit()
	 */
	EVP_CIPHER_CTX *rsaEncryptCtx = EVP_CIPHER_CTX_new();
	if (rsaEncryptCtx == NULL) {
		goto cleanup;
	}

	unsigned char *ek = malloc(EVP_PKEY_size(publicKey));
	int ekLen = 0;
	unsigned char *iv = malloc(EVP_MAX_IV_LENGTH);
	if (ek == NULL || iv == NULL) {
		goto cleanup;
	}

	EVP_SealInit(rsaEncryptCtx, EVP_aes_128_cbc(), &ek, &ekLen, iv, &publicKey, 1);
	for (int i=0; i<ekLen; i++) {
		printf("0x%x ", ek[i]);
	}

	char *message = "123456789abcdef!!";
	int msgLen = strlen(message)+1;

	unsigned char *ctxt = malloc(msgLen + EVP_MAX_IV_LENGTH);
	if (ctxt == NULL) {
		goto cleanup;
	}
	int ctxtLen = 0;
	int tmp = 0;

	// EVP_SealUpdate
	EVP_SealUpdate(rsaEncryptCtx, ctxt, &tmp, message, msgLen);
	ctxtLen += tmp;
	printf("\nmsgLen=%d, tmp=%d\n", msgLen, tmp);

	// EVP_SealFinal
	EVP_SealFinal(rsaEncryptCtx, ctxt+tmp, &tmp);
	ctxtLen += tmp;
	printf("ctxtLen=%d, tmp=%d\n", ctxtLen, tmp);

	/**
	 * De-serialize Bob's private key: char [] => EVP_PKEY
	 */

	// EVP_OpenInit

	// EVP_OpenUpdate

	// EVP_OpenFinal





	//puts("!!!Hello World!!!"); /* prints !!!Hello World!!! */

cleanup:
	if (keyCtx != NULL) EVP_PKEY_CTX_free(keyCtx);
	if (bob_keypair != NULL) EVP_PKEY_free(bob_keypair);
	if (publicBIO != NULL) BIO_free_all(publicBIO);
	if (rsaEncryptCtx != NULL) EVP_CIPHER_CTX_free(rsaEncryptCtx);
	if (ek != NULL) free(ek);
	if (iv != NULL) free(iv);
	if (ctxt != NULL) free(ctxt);


	return EXIT_SUCCESS;
}













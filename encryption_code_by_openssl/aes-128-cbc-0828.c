/*
 ============================================================================
 Name        : aes-128-cbc-0828.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <openssl/evp.h>
#include <openssl/rand.h>
#define CHUNK_SIZE 1024

int main(void) {
	puts("!!!Hello World!!!"); /* prints !!!Hello World!!! */

	/**
	 * allocate cipher context for encryption
	 */
	EVP_CIPHER_CTX *ctx_enc = EVP_CIPHER_CTX_new();
	if (ctx_enc == NULL) {
		goto cleanup;
	}

	/**
	 * initialize cipher context
	 */
	int result = EVP_EncryptInit_ex(ctx_enc, EVP_aes_128_cbc(), NULL, NULL, NULL); // AES 128 in CBC
	if (result != 1) {
		goto cleanup;
	}

	/**
	 * generate random key and iv
	 */
	unsigned char *key = (unsigned char *)malloc(EVP_CIPHER_CTX_key_length(ctx_enc));
	if (key == NULL) {
		goto cleanup;
	}
	RAND_bytes(key, EVP_CIPHER_CTX_key_length(ctx_enc));
	printf("## key bytes ##\n");
	for (int i=0; i<8; i++) {
		printf("0x%X ", key[i]);
	}
	printf("\n");

	unsigned char *iv = (unsigned char *)malloc(EVP_CIPHER_CTX_iv_length(ctx_enc));
	if (iv == NULL) {
		goto cleanup;
	}
	RAND_bytes(iv, EVP_CIPHER_CTX_iv_length(ctx_enc));
	printf("## iv bytes ##\n");
	for (int i=0; i<8; i++) {
		printf("0x%X ", iv[i]);
	}
	printf("\n");

	result = EVP_EncryptInit_ex(ctx_enc, NULL, NULL, key, iv);
	if (result != 1) {
		goto cleanup;
	}

	// file open for ptxt file
	FILE *ifp = fopen("alice29.txt", "r");
	if (ifp == NULL) {
		goto cleanup;
	}

	// file open for ctxt file
	FILE *ofp = fopen("ctxtfile", "w");
	if (ofp == NULL) {
		goto cleanup;
	}

	// allocate inbuf & outbuf
	unsigned char *inbuf = (unsigned char *)malloc(CHUNK_SIZE);
	if (inbuf == NULL) {
		goto cleanup;
	}
	unsigned char *outbuf = (unsigned char *)malloc(CHUNK_SIZE + EVP_CIPHER_CTX_block_size(ctx_enc));
	if (outbuf == NULL) {
		goto cleanup;
	}

	int inlen = 0, tmp=0;
	while ( feof(ifp) != 1 ) {
		inlen = fread(inbuf, sizeof(char), CHUNK_SIZE, ifp);
		result = EVP_EncryptUpdate(ctx_enc, outbuf, &tmp, inbuf, inlen);
		if (result != 1) goto cleanup;
		fwrite(outbuf, sizeof(char), tmp, ofp);
		printf("EncUpdate: inlen=%d, tmp=%d\n", inlen, tmp);
	}

	// EncryptFinal
	result = EVP_EncryptFinal_ex(ctx_enc, outbuf, &tmp);
	if (result != 1) goto cleanup;
	fwrite(outbuf, sizeof(char), tmp, ofp);

	fclose(ifp);
	fclose(ofp);
	EVP_CIPHER_CTX_free(ctx_enc);


	// EncryptFinal













cleanup:
	if (key != NULL) free(key);
	if (iv != NULL) free(iv);
	if (ctx_enc != NULL) EVP_CIPHER_CTX_free(ctx_enc);
	if (ifp != NULL) fclose(ifp);
	if (ofp != NULL) fclose(ofp);
	if (inbuf != NULL) free(inbuf);
	if (outbuf != NULL) free(outbuf);

	return EXIT_SUCCESS;
}

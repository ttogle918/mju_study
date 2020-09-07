#include <stdio.h>
#include <stdlib.h>
#include <openssl/rand.h>
#include <openssl/evp.h>

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
	int result = EVP_EncryptInit_ex(ctx_enc, EVP_des_cbc(), NULL, NULL, NULL);
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

	result = EVP_EncryptInit_ex(ctx_enc, EVP_des_cbc(), NULL, key, iv);
	if (result != 1) {
		goto cleanup;
	}

	// ptxt to encrypt
	char str[] = "123456789abcdef  123456789abcdef  3456789abcdef123456789abcdef";
	// allocate ctxt buf
	unsigned char *ctxt = (unsigned char *)malloc(strlen(str) + EVP_CIPHER_CTX_block_size(ctx_enc));
	if (ctxt == NULL) {
		goto cleanup;
	}

	int ol = 0, tmp = 0, il = 0;
	// EncryptUpdate
	result = EVP_EncryptUpdate(ctx_enc, &ctxt[ol], &tmp, &str[il], strlen(str));
	if (result != 1) {
		goto cleanup;
	}
	ol += tmp;
	il += strlen(str);
	printf("EncUp: ol=%d, tmp=%d, il=%d, ptxt_len=%d\n", ol, tmp, il, strlen(str));

	// EncryptFinal
	result = EVP_EncryptFinal_ex(ctx_enc, &ctxt[ol], &tmp);
	if (result != 1) {
		goto cleanup;
	}
	ol += tmp;
	printf("EncFinal: ol=%d, tmp=%d\n", ol, tmp);

	printf("Plaintext %s\n", str);
	printf("Ciphertext\n");
	for (int i=0; i<ol; i++) {
		printf("0x%X ", ctxt[i]);
	}
	printf("\n");
	//printf("Ciphertext %s\n", ctxt);

	int ctxt_length = ol;

	/**
	 * allocate and initialize cipher context for decryption
	 */
	EVP_CIPHER_CTX *ctx_dec = EVP_CIPHER_CTX_new();
	if (ctx_dec == NULL) {
		goto cleanup;
	}

	result = EVP_DecryptInit_ex(ctx_dec, EVP_des_cbc(), NULL, key, iv);
	if (result != 1) {
		goto cleanup;
	}

	// allocate buffer for recovered ptxt
	unsigned char *recovered_ptxt = (unsigned char *)malloc(ctxt_length);
	if (recovered_ptxt == NULL) {
		goto cleanup;
	}

	ol=0; tmp=0; il=0;
	// DecryptUpdate
	result = EVP_DecryptUpdate(ctx_dec, &recovered_ptxt[ol], &tmp, &ctxt[il], ctxt_length);
	if (result != 1) {
		goto cleanup;
	}
	ol += tmp;
	il += ctxt_length;
	printf("DecUp: ol=%d, tmp=%d, il=%d, ctxt_len=%d\n", ol, tmp, il, ctxt_length);

	// DecryptFinal
	result = EVP_DecryptFinal_ex(ctx_dec, &recovered_ptxt[ol], &tmp);
	if (result != 1) {
		goto cleanup;
	}
	ol += tmp;
	printf("DecFinal: ol=%d, tmp=%d\n", ol, tmp);

	recovered_ptxt[ol] = '\0';

	printf("Plaintext %s\n", str);
	printf("Decrypted %s\n", recovered_ptxt);







cleanup:
	if (key != NULL) free(key);
	if (iv != NULL) free(iv);
	if (ctx_enc != NULL) EVP_CIPHER_CTX_free(ctx_enc);
	if (ctxt != NULL) free(ctxt);
	if (ctx_dec != NULL) EVP_CIPHER_CTX_free(ctx_dec);
	if (recovered_ptxt != NULL) free(recovered_ptxt);

	return EXIT_SUCCESS;
}

















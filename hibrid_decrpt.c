/*
 ============================================================================
 Name        : hybrid.c
 Author      :
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <openssl/evp.h>
#include <openssl/rsa.h>

int main(void) {
	puts("!!!Hello World!!!"); /* prints !!!Hello World!!! */

	/*
	 * generate bob's rsa key pair (struct EVP_PKEY)
	 */
	// allocate and configure context for key pair generation
	EVP_PKEY_CTX *keyCtx = EVP_PKEY_CTX_new_id(EVP_PKEY_RSA, NULL);
	EVP_PKEY_keygen_init(keyCtx);
	EVP_PKEY_CTX_set_rsa_keygen_bits(keyCtx, 2048);	//rsa 2048

	EVP_PKEY *bob_keypair = NULL;
	EVP_PKEY_keygen(keyCtx, &bob_keypair);

	if(bob_keypair == NULL)	goto cleanup;

	EVP_PKEY_CTX_free(keyCtx);
	/*
		 * Serialize bob's private key ( done by bob ) : EVP_PKEY => char[]
		 */

		BIO *privateBIO = BIO_new(BIO_s_mem());
		if(privateBIO==NULL)	goto cleanup;
		PEM_write_bio_PrivateKey(privateBIO, bob_keypair, NULL, NULL, 0,0,NULL);

		int priKeyLen = BIO_pending(privateBIO);
		unsigned char *prikeyChar = malloc(priKeyLen + 1);
		BIO_read(privateBIO, prikeyChar, priKeyLen);
		prikeyChar[priKeyLen] = '\0';

		printf("%s\n", prikeyChar);

	/*
	 * Serialize bob's public key ( done by bob ) : EVP_PKEY => char[]
	 */

	BIO *publicBIO = BIO_new(BIO_s_mem());
	if(publicBIO==NULL)	goto cleanup;
	PEM_write_bio_PUBKEY(publicBIO, bob_keypair);

	int publicKeyLen = BIO_pending(publicBIO);
	unsigned char *pubkeyChar = malloc(publicKeyLen + 1);
	BIO_read(publicBIO, pubkeyChar, publicKeyLen);
	pubkeyChar[publicKeyLen] = '\0';

	printf("%s\n", pubkeyChar);

	/*
	 * Deserialize bob's public key ( done by alice ) : char [] => EVP_PKEY
	 */
	unsigned char *rsaPublicKeyChar = pubkeyChar;
	BIO *rsaPublicBIO = BIO_new_mem_buf(rsaPublicKeyChar, -1);
	RSA *rsaPublicKey = NULL;
	PEM_read_bio_RSA_PUBKEY(rsaPublicBIO, &rsaPublicKey, NULL, NULL);

	EVP_PKEY *publicKey = EVP_PKEY_new();
	EVP_PKEY_assign_RSA(publicKey, rsaPublicKey);

	/*
	 * EVP_SealInit()
	 */
	EVP_CIPHER_CTX *rsaEncryptCtx = EVP_CIPHER_CTX_new();

	unsigned char *ek = malloc(EVP_PKEY_size(publicKey));
	int ekLen = 0;
	unsigned char *iv = malloc(EVP_MAX_IV_LENGTH);

	EVP_SealInit(rsaEncryptCtx, EVP_aes_128_cbc(), &ek, &ekLen, iv, &publicKey, 1);

	for(int i = 0; i < ekLen; i++){
		printf("0x%x", ek[i]);
	}

	char *msg = "imymemaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaine";
	printf("\n%s\n", msg);
	int msgLen = strlen(msg) + 1;
	unsigned char *encryptedMsg = (unsigned char*)malloc(msgLen + EVP_CIPHER_CTX_block_size(rsaEncryptCtx));
	// = malloc(msgLen + EVP_MAX_IV_LENGTH);

	int encryptedMsgLen = 0;
	int encryptedBlockLen = 0;	// = tmp

	EVP_SealUpdate(rsaEncryptCtx, encryptedMsg, &encryptedBlockLen, (const unsigned char*)msg, msgLen);
	encryptedMsgLen = encryptedBlockLen;

	EVP_SealFinal(rsaEncryptCtx, encryptedMsg + encryptedBlockLen, &encryptedBlockLen);
	encryptedMsgLen += encryptedBlockLen;
	/*
	 * De-serialize Bob's private key : char [] => EVP_PKEY
	 */
	unsigned char *rsaPrivateKeyChar = prikeyChar;
	BIO *rsaPrivateBIO = BIO_new_mem_buf(rsaPrivateKeyChar, -1);
	RSA *rsaPrivateKey = NULL;
	PEM_read_bio_RSAPrivateKey(rsaPrivateBIO, &rsaPrivateKey, NULL, NULL);
	EVP_PKEY *privateKey = EVP_PKEY_new();
	EVP_PKEY_assign_RSA(privateKey, rsaPrivateKey);
	EVP_CIPHER_CTX *rsaDecryptCtx = EVP_CIPHER_CTX_new();

	EVP_OpenInit(rsaDecryptCtx, EVP_aes_128_cbc(), ek, ekLen, iv, privateKey);
	// EVP_OpenInit
	unsigned char *decryptedMsg = (unsigned char*)malloc(encryptedMsgLen + EVP_CIPHER_CTX_block_size(rsaDecryptCtx) + 1);
	int decryptedMsgLen = 0;
	int decryptedBlockLen = 0;



	// EVP_OpenUpdate
	EVP_OpenUpdate(rsaDecryptCtx, decryptedMsg, &decryptedBlockLen, encryptedMsg, encryptedMsgLen);

	decryptedMsgLen += decryptedBlockLen;
	decryptedMsg[decryptedMsgLen] = '\0';
	printf("Decrypted: >>%s<<\n", decryptedMsg);
	// EVP_OpenFinal
	EVP_OpenFinal(rsaDecryptCtx, decryptedMsg, decryptedMsgLen );
cleanup :
	if(keyCtx != NULL)	EVP_PKEY_CTX_free(keyCtx);
	if(bob_keypair != NULL)	EVP_PKEY_free(bob_keypair);
	if(publicBIO != NULL)	BIO_free_all(publicBIO);

	return EXIT_SUCCESS;
}


# CryptoExample
Nesse projeto vou mostrar como realizar uma encriptação e decriptação por meio de uma classe utilizando a lib KeyStore e o armazenamento DataStore sendo um projeto usando Compose presentes no documentação oficial do Android.

### [Key Store]
Usado e recomendado pelo Android como uma pratica segura recomendada para fazer a criptografia e decriptografia de dados para qualquer dado usado dentro do app.
O sistema Keystore é usado pela API KeyChain, introduzida no Android 4.0 (API de nível 14); o recurso provedor de Keystore do Android, introduzido no Android 4.3 (API de nível 18) e a Biblioteca de segurança, disponível como parte do Jetpack. Este documento explica quando e como usar o provedor Android Keystore.  

### [Data Store]
O Jetpack DataStore é uma solução de armazenamento de dados que permite armazenar pares de chave-valor ou objetos tipados com buffers de protocolo. O DataStore usa corrotinas e fluxo do Kotlin para armazenar dados de forma assíncrona, consistente e transacional.

### [Serialization] 
Serialização é o processo de conversão de dados usados ​​por um aplicativo em um formato que pode ser transferido por uma rede ou armazenado em um banco de dados ou arquivo. Por sua vez, a desserialização é o processo oposto de leitura de dados de uma fonte externa e conversão em um objeto de tempo de execução. Juntos, eles são uma parte essencial da maioria dos aplicativos que trocam dados com terceiros.

Alguns formatos de serialização de dados, como JSON e buffers de protocolo, são particularmente comuns. Sendo neutros em termos de linguagem e plataforma, eles permitem a troca de dados entre sistemas escritos em qualquer linguagem moderna.

## Projeto
O projeto ele possui duas view sendo uma `MainActivity` e outra `DataStoreActivity`.

Inciando pela `MainActivity`, ela esta mais simples, possui apenas um campo de criptografia onde é mostrado o dado encriptado e depois decriptado e salvo em uma arquivo file dentro do device.

`MainActivity`

![image](https://github.com/renan1820/CryptoExample/assets/33467920/5bd9836e-d2bd-4ed7-b2dc-fc316117a411)
![image](https://github.com/renan1820/CryptoExample/assets/33467920/39fab1ea-a4e7-43ca-8421-f3f8cc53734b)

**Arquivo salvo** dentro das pastas do projeto 

![image](https://github.com/renan1820/CryptoExample/assets/33467920/f51bf5e5-288d-41db-b0a1-8e0c3ef41d60) ![image](https://github.com/renan1820/CryptoExample/assets/33467920/9b48a173-c143-4093-a2e3-422b23a8f8fd)

Composto pelo traço de código no projeto

```kotlin
private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry("secret", null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }
    private fun createKey(): SecretKey{
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder("secret", KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }
```

`DataStoreActivity`

![image](https://github.com/renan1820/CryptoExample/assets/33467920/fce297de-a624-4d2b-afc6-159a4f847cd5)
![image](https://github.com/renan1820/CryptoExample/assets/33467920/157e04b7-bcc6-4d50-a4f2-6c4b8217d45e)

**Arquivo salvo** dentro das pastas do projeto 

![image](https://github.com/renan1820/CryptoExample/assets/33467920/65ebb22f-1468-47f8-9893-8ed848b05ebf)
![image](https://github.com/renan1820/CryptoExample/assets/33467920/60ef7413-ab2c-4179-8c87-373ff8bbe156)


```kotlin
private val Context.dataStore by dataStore(
        fileName = "user-settings.json",
        serializer = UserSettingsSerializer(CryptoManager())
    )
``` 


[Key Store]: https://developer.android.com/training/articles/keystore?hl=pt-br
[Data Store]: https://developer.android.com/topic/libraries/architecture/datastore?hl=pt-br
[Serialization]: https://kotlinlang.org/docs/serialization.html

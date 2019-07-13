package com.britt.listi

import data.UserTokenResponse
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys;
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import javax.xml.bind.DatatypeConverter


/***********************************
 *
 * NOTE! THIS IS NOT SECURE!
 * These fields are kept here for the simplicity of the workshop,
 * but it's definitely NOT the best practice to keep them this way
 * for a real life use case.
 *
 * Beware not to expose your private key to public!
 *
 **********************************/

val nexmoAppId = nexmoAppId()
fun nexmoAppId(): String {
    TODO("return your Nexmo Application ID")
    return ""
}

var privateKey = nexmoPrivateKey()
fun nexmoPrivateKey(): String {
    TODO("return your Nexmo private key")
    return ""
}
private const val PRIVATE_KEY_HEADER = "-----BEGIN PRIVATE KEY-----\n"
private const val PRIVATE_KEY_FOOTER = "-----END PRIVATE KEY-----"

val DAY_MILLIS = 24 * 60 * 60 * 1000

//class ApiAuthManager {
fun generateJwt(sub: String? = null, expiration: Date = Date(System.currentTimeMillis() + DAY_MILLIS)): UserTokenResponse {
    val jwt = Jwts.builder()
        .claim("userId", 1)
        .claim("acl", buildAclMap())
        .claim("application_id", nexmoAppId)
        .setIssuedAt(Date())
        .setSubject(sub)
        .setExpiration(expiration)
        .setId(UUID.randomUUID().toString())
            .signWith(privateKeyFromPem(privateKey))
        .compact()

    return UserTokenResponse(user_name = sub, jwt = jwt)
}

private fun privateKeyFromPem(key: String): PrivateKey {
    val privateKey = key
        .replace(PRIVATE_KEY_HEADER, "")
        .replace(PRIVATE_KEY_FOOTER, "")

    val encoded = DatatypeConverter.parseBase64Binary(privateKey)
    val kf = KeyFactory.getInstance("RSA")
    val keySpec = PKCS8EncodedKeySpec(encoded)
    return kf.generatePrivate(keySpec) as RSAPrivateKey
}

private fun buildAclMap() = mapOf(
    "paths" to mapOf<String, Map<String, Map<Any, Any>>>(
        "/v1/users/**" to emptyMap(),
        "/v1/conversations/**" to emptyMap(),
        "/v1/sessions/**" to emptyMap(),
        "/v1/devices/**" to emptyMap(),
        "/v1/image/**" to emptyMap(),
        "/v3/media/**" to emptyMap(),
        "/v1/applications/**" to emptyMap(),
        "/v1/push/**" to emptyMap(),
        "/v1/knocking/**" to emptyMap()
    )
)
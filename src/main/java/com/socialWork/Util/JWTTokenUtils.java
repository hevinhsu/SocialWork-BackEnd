package com.socialWork.Util;

import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;


public class JWTTokenUtils  {
    static final long EXPIRATIONTIME = 60 * 60;     // 1h
    static final String TOKEN_PREFIX = "Bearer";        // Token前缀
    static final String HEADER_STRING = "Authorization";// 存放Token的Header Key
    static final Key key = MacProvider.generateKey();	//給定一組密鑰，用來解密以及加密使用




    }
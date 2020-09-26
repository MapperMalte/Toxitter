package Toxitter.Security;

import Toxitter.Connection.Middleware;

import java.io.IOException;

public class ToxitterSecurityMiddleware extends Middleware
{
    public static final String TOKEN_IDENTIFIER = "tokenId";
    public static final String TOKEN_IDENTIFIER_2 = "accessToken";

    private static final int TOKEN_IDENTIFIER_LENGTH = TOKEN_IDENTIFIER.length();
}

package Avatar.Security;

import Avatar.Elemental.aether.Middleware;

public class ToxitterSecurityMiddleware extends Middleware
{
    public static final String TOKEN_IDENTIFIER = "tokenId";
    public static final String TOKEN_IDENTIFIER_2 = "accessToken";

    private static final int TOKEN_IDENTIFIER_LENGTH = TOKEN_IDENTIFIER.length();
}

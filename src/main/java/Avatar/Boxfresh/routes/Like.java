package Avatar.Boxfresh.routes;

import Avatar.Elemental.earth.Relation;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.fire.AI.stimulanziae.Dopamine;
import Avatar.Elemental.fire.AI.stimulanziae.GABA;
import Avatar.Elemental.fire.AI.StimulatesAI;
import Avatar.Elemental.fire.AI.stimulanziae.Narcidin;

@StimulatesAI
public class Like extends ReservoirEntity
{
    private static Relation<Post,Reaction> likes;

    private static class Reaction extends ReservoirEntity
    {
        User user;
        ReactionType reaction;
    }

    private static ReservoirEntityList<Reaction> filter(ReservoirEntityList<Reaction> input, ReactionType type)
    {
        ReservoirEntityList<Reaction> result = new ReservoirEntityList<>();

        input.bottom();
        while( !input.isPointerNull() )
        {
            if ( input.getCurrent().reaction.equals(type) ) result.addOnTop(input.getCurrent());
            input.next();
        }

        return result;
    }

    @Narcidin
    @Dopamine(amount = 1)
    public static void like(Post p, User user)
    {
        Reaction reaction = new Reaction();
        reaction.user = user;
        reaction.reaction = ReactionType.LIKE;
        likes.put(p,reaction);
    }

    @Dopamine(amount = 1)
    public static int countLikes(Post p)
    {
        return filter(likes.forwardGet(p), ReactionType.LIKE).getSize();
    }

    @GABA
    public static int countDislikes(Post p)
    {
        return filter(likes.forwardGet(p), ReactionType.DISLIKE).getSize();
    }
}
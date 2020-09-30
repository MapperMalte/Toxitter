package Toxitter.Boxfresh.routes;

import Toxitter.Core.Elemental.earth.Relation;
import Toxitter.Core.Elemental.earth.ReservoirEntity;
import Toxitter.Core.Elemental.earth.ReservoirEntityList;
import Toxitter.Core.Elemental.fire.ai_stimulanziae.Dopamine;
import Toxitter.Core.Elemental.fire.ai_stimulanziae.GABA;
import Toxitter.Core.Elemental.fire.StimulatesAI;

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
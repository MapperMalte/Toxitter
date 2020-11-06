package Avatar.Boxfresh.input;

import Avatar.Elemental.water.INPUT;
import Avatar.Elemental.water.html.annotations.Form;

@Form
public class CreatePostRequest extends INPUT
{
    public String ownerId;
    public String content;
}
package Reference;

public class ShaderTextured extends Shader{

    public ShaderTextured(){
        super("Reference.Texture.vs", "Reference.Texture.fs");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations() {

    }
}

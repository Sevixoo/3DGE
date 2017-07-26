precision mediump float;

uniform sampler2D uTexture0;

in vec2 textureCoord;

out vec4 outColor;

void main(){
    outColor = texture( uTexture0, textureCoord );
}
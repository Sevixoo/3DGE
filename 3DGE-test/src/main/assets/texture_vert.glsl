
layout ( location = 0 ) in vec4 vPosition;
layout ( location = 2 ) in vec2 vTextureCoord;

uniform mat4 uMVPmatrix;

out vec2 textureCoord;

void main(){
    gl_Position = uMVPmatrix * vPosition;
    textureCoord = vTextureCoord;
}
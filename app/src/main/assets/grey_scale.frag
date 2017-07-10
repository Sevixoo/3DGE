precision mediump float;

varying highp vec2 textureCoordinate;
uniform sampler2D vScreenTexture;

void main(){
    gl_FragColor = vec4(vec3(texture2D(vScreenTexture, textureCoordinate)), 1.0);
    float avg = (gl_FragColor.r + gl_FragColor.g + gl_FragColor.b) / 3.0;
    gl_FragColor = vec4(avg, avg, avg, 1.0);
}
varying vec3 vertex;

uniform vec3 lightPosition;
uniform float renderDistance;

void main(void) {

    float distance = length(vertex - lightPosition);

    gl_FragDepth = distance / renderDistance;

}
varying vec3 vertex;

uniform mat4 modelMatrix;
uniform mat4 viewProjectionMatrix;
uniform vec3 modelPosition;

void main(void) {

    vertex = vec3(gl_Vertex) + modelPosition;

    gl_Position = viewProjectionMatrix * modelMatrix * gl_Vertex;

}









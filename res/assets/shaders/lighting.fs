varying vec3 vertex;
varying vec3 originVertex;
varying vec3 normal;

const int maxLightSourceCount = 8;

uniform float materialShininess;
uniform int lightSourceCount;
uniform int[maxLightSourceCount] lightSourceTypes;
uniform vec3[maxLightSourceCount] lightPositions;
uniform vec3[maxLightSourceCount] lightColors;
uniform float[maxLightSourceCount] lightStrengths;
uniform vec3[maxLightSourceCount] lightDirections;
uniform float[maxLightSourceCount] lightRadii;
uniform int[maxLightSourceCount] specularLighting;
uniform float[maxLightSourceCount] lightAngles;
uniform int[maxLightSourceCount] shadowThrowing;
uniform float[maxLightSourceCount] transitions;
uniform float renderDistance;
uniform sampler2D shadowMap0;
uniform sampler2D shadowMap1;
uniform sampler2D shadowMap2;
uniform sampler2D shadowMap3;
uniform sampler2D shadowMap4;
uniform sampler2D shadowMap5;
uniform sampler2D shadowMap6;
uniform sampler2D shadowMap7;
uniform mat4[maxLightSourceCount] shadowMapCoordinates;
uniform mat4 modelMatrix;
uniform sampler2D texture;
uniform vec4 color;
uniform vec3 cameraPosition;
uniform vec3[3] reflectionAssets;
uniform int materialType;
uniform float materialTransparency;
uniform int shadowMapToDisplay;
uniform mat4 viewProjectionMatrix;
uniform int shadowMapFullScreen;

void main(void) {

    vec3 fragColor;

    vec4 previousFragmentColor;

    if (color == vec4(0, 0, 0, 0)) previousFragmentColor = texture2D(texture, vec2(gl_TexCoord[0]));

    else previousFragmentColor = color;

    previousFragmentColor.a *= materialTransparency;

    if (materialType == 0) fragColor = vec3(previousFragmentColor);

    else {

        vec3 ambientLightedTextureColor;

        vec3 ambientColorMultiplier;

        if (reflectionAssets[0] == vec3(0, 0, 0)) ambientColorMultiplier = vec3(0.05, 0.05, 0.05);

        else ambientColorMultiplier = reflectionAssets[0];

        ambientLightedTextureColor = vec3(vec3(previousFragmentColor) * ambientColorMultiplier);

        float shininess;

        if (materialShininess == 0) shininess = 90;

        else shininess = materialShininess;

        int count = 0;

        while (count < lightSourceCount) {

            float inShadow = 1;

            if (shadowThrowing[count] == 1) {

                vec4 shadowMapPosition = shadowMapCoordinates[count] * vec4(vertex, 1);
                vec3 normalizedShadowMapPosition = shadowMapPosition.xyz / shadowMapPosition.w;
                normalizedShadowMapPosition = normalizedShadowMapPosition * 0.5 + 0.5;

                float theoreticalFragmentLightDepth;

                if (count == 0)
                    theoreticalFragmentLightDepth = (texture2D(shadowMap0, normalizedShadowMapPosition.xy).r) * renderDistance;

                else if (count == 1)
                    theoreticalFragmentLightDepth = (texture2D(shadowMap1, normalizedShadowMapPosition.xy).r) * renderDistance;

                else if (count == 2)
                    theoreticalFragmentLightDepth = (texture2D(shadowMap2, normalizedShadowMapPosition.xy).r) * renderDistance;

                else if (count == 3)
                    theoreticalFragmentLightDepth = (texture2D(shadowMap3, normalizedShadowMapPosition.xy).r) * renderDistance;

                else if (count == 4)
                    theoreticalFragmentLightDepth = (texture2D(shadowMap4, normalizedShadowMapPosition.xy).r) * renderDistance;

                else if (count == 5)
                    theoreticalFragmentLightDepth = (texture2D(shadowMap5, normalizedShadowMapPosition.xy).r) * renderDistance;

                else if (count == 6)
                    theoreticalFragmentLightDepth = (texture2D(shadowMap6, normalizedShadowMapPosition.xy).r) * renderDistance;

                else if (count == 7)
                    theoreticalFragmentLightDepth = (texture2D(shadowMap7, normalizedShadowMapPosition.xy).r) * renderDistance;

                float actualFragmentLightDepth = length(vertex - lightPositions[count]);

                inShadow = (actualFragmentLightDepth <= theoreticalFragmentLightDepth + 0.006) ? 1 : 0;

            }

            if (lightSourceTypes[count] == 0) {

                vec3 lightDifference = vertex - lightPositions[count];

                vec3 lightDirection = normalize(lightDifference);

                float actualAngle = acos(dot(lightDirections[count], lightDirection));

                if (actualAngle <= lightAngles[count]) {

                    float difference = length(lightDifference);

                    float diffuseLightIntensity = lightStrengths[count] / difference;
                    diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                    fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * reflectionAssets[1] * lightColors[count]) * inShadow;

                    if (specularLighting[count] == 1 && materialType >= 2) {

                        vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                        vec3 idealReflectionDirection = vec3(normalize(cameraPosition - vertex));

                        float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                        specularLightIntensity = pow(specularLightIntensity, shininess);


                        fragColor += vec3(specularLightIntensity * lightColors[count] * reflectionAssets[2]) * inShadow;

                    }

                } else {

                    if (transitions[count] != 0) {

                        float newLightAngle = lightAngles[count] +  lightAngles[count] * 0.5 * transitions[count];

                        if (actualAngle <= newLightAngle) {

                            float relativeAngleDifference = (1 - (actualAngle - lightAngles[count]) / (newLightAngle - lightAngles[count]));

                            float difference = length(lightDifference);

                            float diffuseLightIntensity = lightStrengths[count] / difference * max(0, dot(normal, -lightDirection)) * relativeAngleDifference;

                            fragColor += vec3(ambientLightedTextureColor * reflectionAssets[1] * lightColors[count] * diffuseLightIntensity) * inShadow;

                            if (specularLighting[count] == 1 && materialType >= 2) {

                                vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                                vec3 idealReflectionDirection = vec3(normalize(cameraPosition - vertex));

                                float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection) * relativeAngleDifference);
                                specularLightIntensity = pow(specularLightIntensity, shininess);


                                fragColor += vec3(specularLightIntensity * lightColors[count] * reflectionAssets[2]) * inShadow;

                            }

                        }

                    }

                }

            } else if (lightSourceTypes[count] == 1) {

                vec3 lightDirection = lightDirections[count];

                float difference = dot(lightDirection, vertex) + dot(-lightDirection, lightPositions[count]);

                if (difference > 0) {

                    float diffuseLightIntensity = lightStrengths[count] / difference;
                    diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                    fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * reflectionAssets[1] * lightColors[count]) * inShadow;

                    if (specularLighting[count] == 1 && materialType >= 2) {

                        vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                        vec3 idealReflectionDirection = normalize(cameraPosition - vertex);

                        float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                        specularLightIntensity = pow(specularLightIntensity, shininess);

                        fragColor += vec3(specularLightIntensity * lightColors[count] * reflectionAssets[2]) * inShadow;

                    }

                }

            } else if (lightSourceTypes[count] == 2) {

                vec3 lightDirection = lightDirections[count];

                float diffuseLightIntensity = lightStrengths[count];
                diffuseLightIntensity *= max(0, dot(normal, -lightDirection));

                fragColor += vec3(ambientLightedTextureColor * diffuseLightIntensity * reflectionAssets[1] * lightColors[count]);

                if (specularLighting[count] == 1 && materialType >= 2) {

                    vec3 reflectionDirection = normalize(reflect(lightDirection, normal));

                    vec3 idealReflectionDirection = normalize(cameraPosition - vertex);

                    float specularLightIntensity = max(0, dot(reflectionDirection, idealReflectionDirection));
                    specularLightIntensity = pow(specularLightIntensity, shininess);

                    fragColor += vec3(specularLightIntensity * lightColors[count] * reflectionAssets[2]);

                }

            }

            count++;

        }

        fragColor += ambientLightedTextureColor;

    }

    if (shadowMapToDisplay != -1) {

        vec4 displayPosition = viewProjectionMatrix * vec4(vertex, 1);
        vec3 normalizedDisplayPosition = displayPosition.xyz / displayPosition.w;
        normalizedDisplayPosition = normalizedDisplayPosition * 0.5 + 0.5;

        if (shadowMapFullScreen == 1) {

            float depthValue;

            if (shadowMapToDisplay == 0)
                depthValue = (texture2D(shadowMap0, normalizedDisplayPosition.xy).r);

            else if (shadowMapToDisplay == 1)
                depthValue = (texture2D(shadowMap1, normalizedDisplayPosition.xy).r);

            else if (shadowMapToDisplay == 2)
                depthValue = (texture2D(shadowMap2, normalizedDisplayPosition.xy).r);

            else if (shadowMapToDisplay == 3)
                depthValue = (texture2D(shadowMap3, normalizedDisplayPosition.xy).r);

            else if (shadowMapToDisplay == 4)
                depthValue = (texture2D(shadowMap4, normalizedDisplayPosition.xy).r);

            else if (shadowMapToDisplay == 5)
                depthValue = (texture2D(shadowMap5, normalizedDisplayPosition.xy).r);

            else if (shadowMapToDisplay == 6)
                depthValue = (texture2D(shadowMap6, normalizedDisplayPosition.xy).r);

            else if (shadowMapToDisplay == 7)
                depthValue = (texture2D(shadowMap7, normalizedDisplayPosition.xy).r);

            fragColor = vec3(depthValue);

        } else {

            if (normalizedDisplayPosition.x >= 0.7) {
                if (normalizedDisplayPosition.y <= 0.3) {

                    vec2 newShadowMapPosition = vec2((normalizedDisplayPosition.x - 0.7) * (10 / 3), normalizedDisplayPosition.y * (10 / 3));

                    float depthValue;

                    if (shadowMapToDisplay == 0)
                        depthValue = (texture2D(shadowMap0, newShadowMapPosition.xy).r);

                    else if (shadowMapToDisplay == 1)
                        depthValue = (texture2D(shadowMap1, newShadowMapPosition.xy).r);

                    else if (shadowMapToDisplay == 2)
                        depthValue = (texture2D(shadowMap2, newShadowMapPosition.xy).r);

                    else if (shadowMapToDisplay == 3)
                        depthValue = (texture2D(shadowMap3, newShadowMapPosition.xy).r);

                    else if (shadowMapToDisplay == 4)
                        depthValue = (texture2D(shadowMap4, newShadowMapPosition.xy).r);

                    else if (shadowMapToDisplay == 5)
                        depthValue = (texture2D(shadowMap5, newShadowMapPosition.xy).r);

                    else if (shadowMapToDisplay == 6)
                        depthValue = (texture2D(shadowMap6, newShadowMapPosition.xy).r);

                    else if (shadowMapToDisplay == 7)
                        depthValue = (texture2D(shadowMap7, newShadowMapPosition.xy).r);

                    fragColor = vec3(depthValue);

                }
            }

        }

    }

    gl_FragColor = vec4(fragColor, previousFragmentColor.a);

}
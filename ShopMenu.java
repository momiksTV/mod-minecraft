plugins {
    id 'eclipse'
    id 'idea'
    id 'maven-publish'
    id 'net.minecraftforge.gradle' version '[6.0,6.2)'
}

version = '1.0.0'
group = 'com.wavesurvival'

base {
    archivesName = 'wavesurvival-1.20.1'
}

java.toolchain.languageVersion = JavaLanguageVersion.of(17)

minecraft {
    mappings channel: 'official', version: '1.20.1'
    
    runs {
        client {
            workingDirectory project.file('run')
            property 'forge.logging.markers', 'REGISTRIES'
            property 'forge.logging.console.level', 'debug'
            mods {
                wavesurvival {
                    source sourceSets.main
                }
            }
        }

        server {
            workingDirectory project.file('run')
            property 'forge.logging.markers', 'REGISTRIES'
            property 'forge.logging.console.level', 'debug'
            mods {
                wavesurvival {
                    source sourceSets.main
                }
            }
        }
    }
}

repositories {
}

dependencies {
    minecraft 'net.minecraftforge:forge:1.20.1-47.2.0'
}

jar {
    manifest {
        attributes([
            'Specification-Title'     : 'wavesurvival',
            'Specification-Vendor'    : 'YourName',
            'Specification-Version'   : '1',
            'Implementation-Title'    : project.name,
            'Implementation-Version'  : project.jar.archiveVersion,
            'Implementation-Vendor'   : 'YourName',
        ])
    }
}

publishing {
    publications {
        mavenJava(MavenPublication) {
            artifact jar
        }
    }
}

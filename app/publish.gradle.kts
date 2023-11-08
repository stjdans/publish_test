import java.io.FileInputStream
import java.util.Properties

println("-----------publish gradle-------------")

apply(plugin = "maven-publish")

println("project : $project")
println("buildDir : $buildDir")

val properties = Properties()
properties.load(FileInputStream(rootProject.file("github_publish_test.properties")))

val BUILD_DIR = "$buildDir"
val OWNER = properties.getProperty("github.owner")
val REPOSITORY = properties.getProperty("github.repository")
val USERNAME = properties.getProperty("github.username")
val TOKEN = properties.getProperty("github.token")
val GROUP_ID = properties.getProperty("github.group_id")
val ARTIFACT_ID = properties.getProperty("github.artifact_id")
val VERSION = properties.getProperty("github.version")

println("""
    BUILD_DIR : $BUILD_DIR
    
    OWNER : $OWNER
    REPOSITORY : $REPOSITORY
    USERNAME : $USERNAME
    TOKEN : $TOKEN
        
    GROUP_ID : $GROUP_ID
    ARTIFACT_ID : $ARTIFACT_ID
    VERSION : $VERSION
    
""".trimIndent())

configure<PublishingExtension> {
    repositories {
        // github 배포
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/${OWNER}/${REPOSITORY}")
            credentials {
                username = USERNAME
                password = TOKEN
            }
        }

        // 모듈 빌드 디렉토리 배포
        maven {
            name = "build-local"
            url = uri("$BUILD_DIR/repo")
        }

        // 메이븐 로컬 디렉토리 배포 (.m2)
        maven {
            name = "maven-local"
        }
    }

    publications {
        register<MavenPublication>("Aar") {
            groupId = "$GROUP_ID"
            artifactId = "$ARTIFACT_ID"
            version = "$VERSION"

            artifact("$BUILD_DIR/outputs/aar/app-debug.aar")
        }
    }
}
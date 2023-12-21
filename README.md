# SimplyFi SDK

## Installation

1. Set env variable `GITLAB_TOKEN` to your token

### NPM

1. Add `.npmrc` to repository root:

```text
@simplyfi:registry=https://gitlab.tenderhub.net/api/v4/projects/309/packages/npm/
//gitlab.tenderhub.net/api/v4/projects/309/packages/npm/:_authToken="${GITLAB_TOKEN}"
```

2. Add dependency to `package.json`:

```json
{
  "dependencies": {
    "@simplyfi/sdk-browser": "0.0.1"
  }
}
```

3. Run in terminal:

```shell
npm install
```

### Maven

1. Add repository to `build.gradle.kts` or `settings.gradle.kts`:

```kotlin
repositories {
    maven {
        url = uri("https://gitlab.tenderhub.net/api/v4/projects/309/packages/maven")
        name = "gitlab"
        credentials(HttpHeaderCredentials::class) {
            name = "Private-Token"
            value = providers.environmentVariable("GITLAB_TOKEN").get()
        }
        authentication {
            create("header", HttpHeaderAuthentication::class)
        }
    }
}
```

2. Add dependency:

```kotlin
kotlin {
    sourceSets {
        // for Kotlin Multiplatform add only this
        val commonMain by getting {
            dependencies {
                implementation("com.simplyfi:sdk:0.0.1")
            }
        }
        // for Android
        val androidMain by getting {
            dependencies {
                implementation("com.simplyfi:sdk-android:0.0.1")
            }
        }
        // for JVM
        val jvmMain by getting {
            dependencies {
                implemenetation("com.simplyfi:sdk-jvm:0.0.1")
            }
        }
    }
}
```

3. Sync project

### SPM

1. Add packages from `git@gitlab.tenderhub.net:sdk/swift-shared.git`

## Demo

1. To build and launch sample apps add `sample/shared/src/commonMain/resources/MR/base/strings.xml` file:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="WEB_URL">{{ URL to web app, e.g. https://go.simplyfi.com }}</string>
    <string name="API_URL">{{ URL to API, e.g. https://api.simplyfi.com }}</string>
    <string name="API_KEY">{{ Your organization API KEY }}</string>
</resources>
```
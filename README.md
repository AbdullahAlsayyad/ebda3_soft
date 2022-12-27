This app for add accounts and show them.

I use custom view when add username



minSdk 21

%%%%%%%%%%%%%%% Add %%%%%%%%%%%%%%%
############### Gradle App #################

%minSdk 21%

dependencies {

    //...

    // Room components
    implementation "androidx.room:room-runtime:$rootProject.roomVersion"
    annotationProcessor "androidx.room:room-compiler:$rootProject.roomVersion"

    // Lifecycle components
    implementation "androidx.lifecycle:lifecycle-viewmodel:$rootProject.lifecycleVersion"
    implementation "androidx.lifecycle:lifecycle-livedata:$rootProject.lifecycleVersion"
}
############### Gradle App #################



############### Gradle Project #################
ext {

    //...

    //roomVersion
    roomVersion = '2.3.0'

    //lifecycleVersion
    lifecycleVersion = '2.3.1'
}
############### Gradle Project #################
%%%%%%%%%%%%%%% Add %%%%%%%%%%%%%%%

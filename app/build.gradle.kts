plugins {
    id(Conventions.androidApp)

    // Firebase Crashlytics
    alias(libs.plugins.firebase.crashlytics)
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.config)

    implementation(libs.androidx.work.runtime.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Features
    implementation(projects.feature.splash)
    implementation(projects.feature.home)
    implementation(projects.feature.socialList)
    implementation(projects.feature.permissions)
}
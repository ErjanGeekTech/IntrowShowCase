plugins {
    id(Conventions.androidFeature)
}

dependencies {
    implementation(projects.feature.socialList)
    implementation(projects.feature.permissions)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
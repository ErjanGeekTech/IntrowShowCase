plugins {
    id(Conventions.androidLibrary)
}

dependencies {
    implementation(libs.review.ktx)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.lifecycle.viewmodel.ktx)

    implementation(platform(libs.compose.bom))
    implementation(libs.runtime)
}
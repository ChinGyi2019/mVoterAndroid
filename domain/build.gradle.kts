plugins{
  id("java-library")
  id("kotlin")
}


dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

  implementation(Kotlin.stdblib_jdk)
  api(KotlinCoroutine.core)

  implementation(CommonLibs.javaxInject)
}
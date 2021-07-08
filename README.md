# nbt

**Basic library for reading and writing Minecraft's NBT format.**

### Usage

This library can be added as a dependency via maven central:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation "dev.dewy:nbt:1.1.0"
}
```

See the [NBTTest](src/test/java/dev/dewy/nbt/test/NBTTest.java) class for sample usage.

### Features

- Fully compliant with Mojang's "standards"
- Small and lightweight
- Supports all Java edition NBT tags (including long array)
- Intuitive and flexible reading and writing functionality

### Planned

- SNBT (Stringified NBT) support for Java and Bedrock
- BNBT (Bedrock NBT) support
- ENBT (Extended NBT) format (missing data types e.g., double array) support
- JSON (De)serialization

### License

This project is licensed under the MIT license: see [here](LICENSE.md).

# nbt

**Basic library for reading and writing Minecraft's NBT format.**

### Usage

This library can be added as a dependency via maven central:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation "dev.dewy:nbt:1.1.1"
}
```

#### NBTReader Sample: Base64

The NBTReader class can be used to easily (de)serialize NBT data:

```java
RootTag root = NbtReader.fromBase64("CgALaGVsbG8gd29ybGQDAAR0ZXN0AAAAAAA=");

System.out.println(root.getName()); // hello world
System.out.println(root.getCompound().getInt("test").getValue()); // 0
```

See the [NbtTest](src/test/java/dev/dewy/nbt/test/NbtTest.java) class for full sample usage.

### Features

- Fully compliant with Mojang's "standards"
- Small and lightweight (32Kb!)
- Supports all Java edition NBT tags (including long array)
- Intuitive and flexible reading and writing functionality

### Planned

- SNBT (Stringified NBT) support for Java and Bedrock
- BNBT (Bedrock NBT) support
- ENBT (Extended NBT) format (missing data types e.g., double array) support
- JSON (De)serialization

### License

This project is licensed under the MIT license: see [here](LICENSE.md).

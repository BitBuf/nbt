<h1 align="center">
  <br>
  <a href="https://github.com/iBuyMountainDew/nbt"><img height="256" src="https://i.imgur.com/zcgQqGf.png" alt="Devent - Dewy's Event System"></a>
  <br>
  nbt
  <br>
</h1>

<h4 align="center">Basic library for reading and writing Minecraft's NBT format.</h4>

<p align="center">
  <a href="#overview">Overview</a>
  •
  <a href="#usage">Usage</a>
  •
  <a href="#features">Features</a>
  •
  <a href="#javadocs">Javadocs</a>
  •
  <a href="#license">License</a>
</p>

### Overview

NBT (Named Binary Tag) is a binary format devised by Notch to be Minecraft's primary means of data storage.

There are 12 types of tag that can be used in a complaint NBT file (see [TagType.java](src/main/java/dev/dewy/nbt/utils/TagType.java) for descriptions of each):

- [Byte](src/main/java/dev/dewy/nbt/tags/number/ByteTag.java)
- [Short](src/main/java/dev/dewy/nbt/tags/number/ShortTag.java)
- [Int](src/main/java/dev/dewy/nbt/tags/number/IntTag.java)
- [Long](src/main/java/dev/dewy/nbt/tags/number/LongTag.java)
- [Float](src/main/java/dev/dewy/nbt/tags/number/FloatTag.java)
- [Double](src/main/java/dev/dewy/nbt/tags/number/DoubleTag.java)
- [Byte Array](src/main/java/dev/dewy/nbt/tags/array/ByteArrayTag.java)
- [Int Array](src/main/java/dev/dewy/nbt/tags/array/IntArrayTag.java)
- [Long Array](src/main/java/dev/dewy/nbt/tags/array/LongArrayTag.java)
- [String](src/main/java/dev/dewy/nbt/tags/StringTag.java)
- [List](src/main/java/dev/dewy/nbt/tags/ListTag.java)
- [Compound](src/main/java/dev/dewy/nbt/tags/CompoundTag.java)                                                       

All valid NBT structures begin with a compound tag; the root compound (abstracted as the [root tag](src/main/java/dev/dewy/nbt/tags/RootTag.java)). Everything else in the NBT structure is a child of this root compound.

### Usage

This library can be added as a dependency via maven central:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation "dev.dewy:nbt:1.2.0"
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

#### Planned

- SNBT (Stringified NBT) support for Java and Bedrock
- BNBT (Bedrock NBT) support
- ENBT (Extended NBT) format (missing data types e.g., double array) support
- JSON (De)serialization

### Javadocs

Javadocs for the library can be found [here](https://javadoc.io/doc/dev.dewy/nbt/latest/index.html).

### License

This project is licensed under the MIT license: see [here](LICENSE.md).

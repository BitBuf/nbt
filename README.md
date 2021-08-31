<h1 align="center">
  nbt
  <br>
</h1>

<h4 align="center">Flexible and intuitive library for reading and writing Minecraft's NBT format.</h4>

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

There are 12 types of tag that can be used in a complaint NBT file (see the links below for descriptions of each):

- [Byte](src/main/java/dev/dewy/nbt/tags/primitive/ByteTag.java)
- [Short](src/main/java/dev/dewy/nbt/tags/primitive/ShortTag.java)
- [Int](src/main/java/dev/dewy/nbt/tags/primitive/IntTag.java)
- [Long](src/main/java/dev/dewy/nbt/tags/primitive/LongTag.java)
- [Float](src/main/java/dev/dewy/nbt/tags/primitive/FloatTag.java)
- [Double](src/main/java/dev/dewy/nbt/tags/primitive/DoubleTag.java)
- [Byte Array](src/main/java/dev/dewy/nbt/tags/array/ByteArrayTag.java)
- [Int Array](src/main/java/dev/dewy/nbt/tags/array/IntArrayTag.java)
- [Long Array](src/main/java/dev/dewy/nbt/tags/array/LongArrayTag.java)
- [String](src/main/java/dev/dewy/nbt/tags/primitive/StringTag.java)
- [List](src/main/java/dev/dewy/nbt/tags/collection/ListTag.java)
- [Compound](src/main/java/dev/dewy/nbt/tags/collection/CompoundTag.java)                                                       

All valid NBT structures begin with a compound tag; the root compound. Everything else in the NBT structure is a child of this root compound.

The original [NBT specification](NBT.txt) written by Notch is also hosted here.

### Usage

This library can be added as a dependency via maven central:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation "dev.dewy:nbt:1.3.3"
}
```

#### NBTReader Sample: Base64

The [Nbt](src/main/java/dev/dewy/nbt/Nbt.java) class can be used to easily (de)serialize NBT data:

```java
public static final Nbt NBT = new Nbt();
```

```java
CompoundTag test = NBT.fromBase64("CgALaGVsbG8gd29ybGQDAAR0ZXN0AAAAAAA");

System.out.println(test.getName()); // hello world
System.out.println(test.getInt("test").getValue()); // 0
```

See the [NbtTest](src/test/java/dev/dewy/nbt/test/NbtTest.java) class for full sample usage.

### Features

- Fully compliant with Mojang's "standards"
- Small and lightweight (40Kb!)
- Supports all Java edition NBT tags (including long array)
- Intuitive and flexible reading and writing functionality

#### Planned

- SNBT (Stringified NBT) support
- ENBT (Extended NBT) format (missing data types e.g., double array) support

### Javadocs

Javadocs for the library can be found [here](https://javadoc.io/doc/dev.dewy/nbt/latest/index.html).

### License

This project is licensed under the MIT license: see [here](LICENSE.md).

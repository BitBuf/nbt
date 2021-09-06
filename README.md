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

Due to the scuffed way the NBT format works, you'll probably want to read up on it before using this library in production.
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

Tag type IDs 1-12 for each of these can be found [here](src/main/java/dev/dewy/nbt/tags/TagType.java).

All valid NBT structures begin with a compound tag; the root compound. Everything else in the NBT structure is a child of this root compound.

The original [NBT specification](NBT.txt) written by Notch is also hosted here.

### Usage

This library can be added as a dependency via maven central:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation "dev.dewy:nbt:1.5.0"
}
```

#### `Nbt` Sample: Base64

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


#### SNBT Format

SNBT (Stringified NBT) is a format defined by Mojang used to record NBT tags as readable strings, used in command blocks.

The [JSON NBT sample](samples/sample.json) encoded as SNBT is as follows:

```text
{"primitive":3,"array":[I;0,1,2,3],"list":["duck","goose"],"compound":{}}
```

#### NBT JSON Format

Every tag in NBT JSON is represented as an object containing the following properties:

- `type` The tag's type ID. (*byte*)
- `value` The tag's value. (*JSON primitive, array, or object depending on the tag's type*)
- `name` The tag's name, omitted if contained inside a [list tag](src/main/java/dev/dewy/nbt/tags/collection/ListTag.java). (*string*)

The [JSON NBT sample](samples/sample.json) is documented below:

```js
{ // root compound tag named "root"
  "type": 10, // compound tag type ID
  "name": "root",
  "value": {
    "primitive": { // an int tag named "primitive" with the value 3
      "type": 3, // int tag type ID
      "name": "primitive",
      "value": 3
    },
    "array": { // an int array tag named "array" with the value [0, 1, 2, 3]
      "type": 11, // int array tag type ID
      "name": "array",
      "value": [
        0,
        1,
        2,
        3
      ]
    },
    "list": { // a list tag named "list" containing string tags.
      "type": 9, // list tag type ID
      "listType": 8, // string tag type ID (this list contains string tags)
      "name": "list",
      "value": [
        { // unnamed string tag contained within "list"
          "type": 8, // string tag type ID
          "value": "duck"
        },
        {
          "type": 8,
          "value": "goose"
        }
      ]
    },
    "compound": { // an empty compound tag named "compound"
      "type": 10, // compound tag type ID
      "name": "compound",
      "value": {} // empty
    }
  }
}
```

### Features

- Fully compliant with Mojang's "standards"
- Small and lightweight (40Kb!)
- Supports all Java edition NBT tags (including long array)
- Intuitive and flexible reading and writing functionality
- JSON (De)serialization
- SNBT Serialization

### Javadocs

Javadocs for the library can be found [here](https://javadoc.io/doc/dev.dewy/nbt/latest/index.html).

### License

This project is licensed under the MIT license: see [here](LICENSE.md).

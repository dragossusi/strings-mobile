# Strings-Mobile

Generate Android and iOS text resources from CSV files


## Installation
Use gradle to build the executable.

Easiest way is to run this command in a terminal:

```./gradlew build```

The executable can be found in the build folder:

```./build/bin/{platform}/{type}Executable```

`platform` is the platform used(linux, macos or windows)
`type` is the build type, `debug`/`release`

## Usage:

### CSV file

The CSV file must be in this format:

|           | EN    | RO    |
|-----------|-------|-------|
| key_hello | Hello | Salut |
| key_world | World | Lume  |

You can use how many keys you want, and they can be named however you desire.
You can also use how many languages you want, add a new column with its code as header, they must be unique.

The program will fail sometimes if you keep some translations empty.

### Generate resource files

To generate all the files in the current folder:
```strings-mobile file.csv```

The program comes with a help command, you can use it to check more options for the executable.
```strings-mobile -h```

# IDFK
A Java-based global key listener that detects specific macro sequences and triggers system-level commands using [NirCmd](https://www.nirsoft.net/utils/nircmd.html). Built with [`jnativehook`](https://github.com/kwhat/jnativehook) to support native keyboard event capturing across platforms.

## Features
- Listens for global key events (works outside of application focus).
- Detects predefined macro keywords.
- Executes system commands silently using `nircmd.exe`.

## Requirements
- Java 17+
- [jnativehook](https://github.com/kwhat/jnativehook) library
- [NirCmd](https://www.nirsoft.net/utils/nircmd.html) (`nircmd.exe`) in your system path or working directory

## Dev notes
**Adding macros:**
- see https://www.nirsoft.net/utils/nircmd.html
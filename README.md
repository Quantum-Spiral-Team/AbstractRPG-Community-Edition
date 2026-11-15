# AbstractRPG Reworked

An unofficial, community-driven spiritual successor to **AbstractRPG** by VivernBar, rewritten for **Cleanroom Loader**.

## About the Project

AbstractRPG Reworked is a ground-up rewrite of [AbstractRPG](https://www.curseforge.com/minecraft/mc-mods/abstract-rpg), a Minecraft 1.12.2 mod created by **VivernBar**, on a modern foundation.

The code is restructured and rewritten and targets Cleanroom Loader. It is **not** a clean-room reimplementation: the maintainers have the original source code, shared by VivernBar, and use it as a basis where applicable. The original artistic assets (textures, models, etc.) are carried over. The project is a modified version of the original mod and, as such, remains the property of VivernBar and is distributed under his license (see [License](#license)).

AbstractRPG Reworked is the next stage of *AbstractRPG: Community Edition* (CE) and is developed in this same repository, in a separate branch. CE aimed to fix and refactor the legacy code. After evaluating that codebase, we concluded that more than half of it would have to be rewritten anyway, so we chose to start over on a modern foundation instead.

- **With the author's involvement**: VivernBar approved the community's work on AbstractRPG in April 2026 and has since shared the original source code with the maintainers. The project is maintained independently of him. If he asks us to change or remove anything, we will comply.
- **Non-profit and free**: We do not seek profit. Official builds are free and will stay free. Selling the mod, its variants, or any part of it (including textures and models) is not permitted.
- **Faithful to the original**: The goal is to reproduce the original mod's features and mechanics, not to invent new content. Anything beyond the original comes only after parity is reached.

> **Compatibility notice:** although the `modId` has not changed, we do not guarantee compatibility of worlds or addons with the original AbstractRPG, AbstractRPG: Community Edition, or between versions of AbstractRPG Reworked itself. The mod may undergo drastic changes during development that break compatibility.

## Our Goals

- **Modern foundation**: Build on Cleanroom Loader and its modern toolchain (current Java and LWJGL 3) instead of fighting legacy Forge limitations.
- **Feature parity**: Reimplement the original mod's features and mechanics, including those that were left unfinished in the original.
- **Clean architecture**: Write readable, maintainable, developer-friendly code from the start.
- **Stability**: Avoid the crashes and compatibility conflicts that plagued the legacy codebase.

## Branches

- `experimental/reworked`: AbstractRPG Reworked, the Cleanroom rewrite (this README).
- `legacy-ce`: AbstractRPG: Community Edition for Forge 1.12.2. It has its own README and license (`LICENSE.md`).

## Requirements

- Minecraft **1.12.2**
- [Cleanroom Loader](https://github.com/CleanroomMC/Cleanroom) (Java <version> or newer)
- [Baubles](https://www.curseforge.com/minecraft/mc-mods/baubles) (recommended external dependency; install it separately)
- Not compatible with Forge, the original AbstractRPG, or AbstractRPG: Community Edition

## Status & Downloads

The project is in **early development** and is not yet playable.

- **Stable Releases**: Once a stable, polished version is ready, it will be published in the [Releases](https://github.com/Quantum-Spiral-Team/AbstractRPG-Community-Edition/releases) tab.
- **Experimental**: Development builds are available from the **GitHub Actions** tab.
  > **Important:** Please only download builds that have successfully passed the server test! Experimental builds are used at your own risk.

## Roadmap

Development progress is tracked in [ROADMAP.md](ROADMAP.md): a checklist of all the original mod's content that is to be recreated.

> **Note:** the roadmap is written in Russian, and a translation is not planned. It is a working progress tracker for the duration of development.

## Contributing

The best way to help right now is to report bugs, crashes, and compatibility problems in the [Issues](https://github.com/Quantum-Spiral-Team/AbstractRPG-Community-Edition/issues) tab (English and Russian are both welcome).

If you contribute code or other content, please note:

- This project is a modified version of AbstractRPG, so all contributions are made under the same [license](LICENSE) as the rest of the project and become part of the work covered by it.
- Original assets may not be extracted or reused outside the terms of the license.
- Do not remove or alter links, mentions of the original author, or license files.

## License

AbstractRPG Reworked is a modified version of AbstractRPG and is distributed under the original license by **© VivernBar** (see [LICENSE](LICENSE); the current text is also published on the [CurseForge page](https://www.curseforge.com/minecraft/mc-mods/abstract-rpg/license) of the original mod). It applies to the whole project: source code, assets, and builds.

In short (the full text in [LICENSE](LICENSE) is authoritative):

- Modified versions may be published, alone or as part of a modpack, but they remain the property of the original author and must be licensed under the same license. The right to publish may be revoked by the author at any time.
- Links, mentions of the original author, and license files must not be removed.
- The mod, its variants, modpacks containing it, and any part of it (including textures, models, and other original content) may not be sold directly without the author's permission. Indirect income, such as ad revenue from videos or donations, is allowed.
- The mod is provided "as is", without warranties.

This project is not an official product of Mojang Studios or Microsoft and is not approved by or associated with them.

## Credits

- **AbstractRPG**: original concept, design, and assets by [VivernBar](https://www.curseforge.com/minecraft/mc-mods/abstract-rpg)
- **Maintainers**: Bronit (Bron1t) and the QS Team
- **Baubles** by Azanor, used as an external dependency

## Contact

Questions, suggestions, or bug reports are always welcome. Open an issue in this repository, or reach us in the official AbstractRPG Discord server.

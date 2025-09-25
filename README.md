<p>
  <img src="https://cdn.genesis.li/assets/images/armstrong/armstrong-round.png"
       alt="Armstrong logo" width="170" align="left" />
</p>

### 🚀 Armstrong
[![Discord](https://img.shields.io/discord/1332991105695875123?logo=discord&label=discord)](https://discord.genesis.li)
[![Latest](https://img.shields.io/github/v/release/Vaption/Armstrong.svg)](https://github.com/Vaption/Armstrong/releases/latest)
[![License](https://img.shields.io/github/license/Vaption/Armstrong.svg)](https://github.com/Vaption/Armstrong/blob/main/LICENSE)

**Armstrong** is a simple plugin that utilizes
[LunarClient](https://lunarclient.com)'s [Apollo API](https://github.com/LunarClient/Apollo).   
**Found a bug or have a feature idea?** Use the [issues](https://github.com/Vaption/Armstrong/issues) tab!

<br>

## ⚖️ Armstrong vs. LunarUtility
Originally the goal was just to migrate the codebase to use Apollo rather than the old BukkitAPI.  
During the migration, I noticed that Apollo (on its own) now does some of what this plugin once did.  
Therefore, the waypoints & disabled-mods module have been removed from this plugin (now configurable in Apollo).  
The cooldown module implementation was also removed, but will come back to it after some higher priority updates!

## 🙌 Credits
Armstrong is based off of [LunarUtility](https://github.com/RefineDevelopment/LunarUtility). All credit to the original developers!

👨‍💻 **Contributors**  
- 🏙️ [Dubai](https://github.com/GamerRealm) – Original Author  
- 🛠️ [Komek](https://github.com/Komeek) – Maintainer  
- 💡 [kayalust](https://github.com/kayalust) – Contributor  

## 💬 Contact
- [Discord](https://discord.genesis.li)

## 📥 Installing
1️⃣ Download the jar from [releases](https://github.com/Vaption/Armstrong/releases)  
2️⃣ Download LunarClient's Apollo API jar from [here](https://github.com/LunarClient/Apollo/releases)  
3️⃣ Drop **both** jars into all your servers  
4️⃣ Edit the configuration files to your liking  

## 📜 Terms of Use
- No selling or claiming this project as your own  
- Please respect & credit the developers  

## ✨ Features
<details>
  <summary>🏷️ <b>Nametag</b></summary>
  Enabled by default, configurable in <code>config.yml</code>.  
  <img src="https://user-images.githubusercontent.com/42650369/154859444-55ffb81b-06b9-497a-9ec5-6c16906b2b83.png" />
</details>

<details>
  <summary>🔒 <b>Require LunarClient</b></summary>
  Disabled by default. Enable by setting <code>REQUIRE-LUNAR</code> to <code>true</code> in <code>config.yml</code>.  
  <img src="https://github.com/RefineDevelopment/LunarUtility/assets/109939794/d3491af0-22bb-4b71-9355-5a9c194dc6ad" />
</details>

<details>
  <summary>🛡️ <b>Staff Mods</b></summary>
  Requires <code>armstrong.staff</code> permission. Toggle with <code>/lsm &lt;player&gt;</code>.  
  <img src="https://user-images.githubusercontent.com/42650369/138829302-7aeaad61-6cf4-426f-954a-43ace12a972f.png" />
</details>

<details>
  <summary>🌙 <b>Player on Lunar</b></summary>
  Requires <code>armstrong.players</code> permission. Check if someone is using LunarClient with <code>/lc &lt;player&gt;</code>.  
  <img src="https://user-images.githubusercontent.com/42650369/138829302-7aeaad61-6cf4-426f-954a-43ace12a972f.png" />
</details>

<details>
  <summary>📋 <b>All Players on Lunar</b></summary>
  Requires <code>armstrong.players</code> permission. Get a list of players on LunarClient with <code>/lc players</code> (or <code>list</code> / <code>users</code>).  
  <img src="https://user-images.githubusercontent.com/42650369/138829302-7aeaad61-6cf4-426f-954a-43ace12a972f.png" />
</details>

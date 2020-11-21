# Discord Bot on Java
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9f94ff9475fe449c82fca1262610496f)](https://app.codacy.com/gh/megoRU/DiscordBot?utm_source=github.com&utm_medium=referral&utm_content=megoRU/DiscordBot&utm_campaign=Badge_Grade)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=alert_status)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=security_rating)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=coverage)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=bugs)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=code_smells)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=ncloc)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=sqale_index)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)

## LICENSE

<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License</a>.

## JETBRAINS
<a href="https://www.jetbrains.com/?from=DiscordBot"><img width="377" height="128" src="jetbrains.png">

## Add bot to your server
<a href="https://discord.com/oauth2/authorize?client_id=754093698681274369&scope=bot&permissions=8"> <img src="https://megolox.ru/gitResources/addtoserver.png" alt="Пример"></a>

## What the bot can do

1.  [Delete messages](#delete-messages)
2.  Move a specific client
3.  [Available Commands](#available-commands)
4.  [Write to the special channel when someone entered/left]()
5.  Converts YouTube links to timecode links
6.  Bot welcome msg when Bot join Guild
7.  [Change channel bitrate](#change-channel-bitrate)
8.  [Exchange Rates (DOLLAR/EURO to RUB)](#exchange-rates)
9.  [Exchange Values](#exchange-values)
10. Repeat after me
11. Game !roll
11. Game !hg (Exclusive for Russian players)


## TODO

-   [x]   Channel for logging movements, enters and deleting messages
-   [x]   Connection counter. Quantity output on command
-   [x]   Make a database to save data
-   [x]   Top 3 users connections to the channels
-   [x]   Rewrite support code for multi-servers
-   [x]   Make two games
-   [ ]   Full english/russian support for outputs
-   [ ]   Command usage counter. Quantity output on command
-   [ ]   [Exchange Rates](#exchange-rates) Do it through xml parsing, not through jsoup

## Games

| Виселица (!hg)                                   | Game of dice (!roll)                                |
| ------------------------------------------------ | --------------------------------------------------- |
| ![](https://megolox.ru/gitResources/hangman.jpg) | ![](https://megolox.ru/gitResources/gameofdice.png) |

## Delete messages

| Deleting multiple messages (clear 10)                      | Checking Administrator's rights to delete messages          |
| ---------------------------------------------------------- | ----------------------------------------------------------- |
| ![](https://megolox.ru/gitResources/deleteTenMessages.png) | ![](https://megolox.ru/gitResources/PrivilegesDeliting.png) |

## Available Commands

| info/help/!help                                |  ping/!ping                                   |  uptime/!uptime                                 |
| ---------------------------------------------- | --------------------------------------------- | ----------------------------------------------- |
| ![](https://megolox.ru/gitResources/info2.png) | ![](https://megolox.ru/gitResources/ping.png) | ![](https://megolox.ru/gitResources/uptime.png) |

## Change channel bitrate

Changes the bitrate of the channel, when a specific user enters. Optimization for the mobile user.

## Exchange Rates

So far, only the dollar, the euro against the ruble.

| курс доллара                                           |
| ------------------------------------------------------ |
| ![](https://megolox.ru/gitResources/ExchangeRates.png) |

## Exchange Values

| 100 рублей в долларах                                   | 100 долларов в рублях                                    |
| ------------------------------------------------------- | -------------------------------------------------------- |
| ![](https://megolox.ru/gitResources/ExchangeValues.png) | ![](https://megolox.ru/gitResources/ExchangeValues2.png) |

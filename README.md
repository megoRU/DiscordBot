# Discord Bot on Java
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9f94ff9475fe449c82fca1262610496f)](https://app.codacy.com/gh/megoRU/DiscordBot?utm_source=github.com&utm_medium=referral&utm_content=megoRU/DiscordBot&utm_campaign=Badge_Grade)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=alert_status)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=security_rating)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=coverage)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=bugs)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=code_smells)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=ncloc)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)     [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=megoRU_DiscordBot&metric=sqale_index)](https://sonarcloud.io/dashboard?id=megoRU_DiscordBot)

## What the bot can do:

1. [Delete messages](#delete-messages)
2. Move a specific client
3. [Available Commands](#available-commands)
4. [Write to the special channel when someone entered/left]()
5. YouTube link + space + time in minutes! -> converts to a short link with time (for phones users). Example: link 1 20 or link 1. First -> minutes. Second -> seconds 
6. Bot welcome msg when Bot join Guild
7. [Change channel bitrate](#change-channel-bitrate)
8. [Exchange Rates (DOLLAR/EURO to RUB)](#exchange-rates)
9. [Exchange Values](#exchange-values)

## TODO

- [ ]   Channels for logging movements and enters
- [ ]   Connection counter. Quantity output on command
- [ ]   Command usage counter. Quantity output on command
- [ ]   Make a database to save data
- [ ]   [Exchange Rates](#exchange-rates) Do it through xml parsing, not through jsoup

## Delete messages

Deleting multiple messages [clear 10] |  Checking Administrator's rights to delete messages
:-------------------------:|:-------------------------:
![](https://megolox.ru/gitResources/deleteTenMessages.png) | ![](https://megolox.ru/gitResources/PrivilegesDeliting.png)

## Available Commands

info/help/!help            |  ping/!ping               |  uptime/!uptime
:-------------------------:|:-------------------------:|:-------------------------:
![](https://megolox.ru/gitResources/info2.png) | ![](https://megolox.ru/gitResources/ping.png)| ![](https://megolox.ru/gitResources/uptime.png)

## Change channel bitrate

Changes the bitrate of the channel, when a specific user enters. Optimization for the mobile user.

## Exchange Rates

So far, only the dollar, the euro against the ruble.

курс доллара               |
:-------------------------:|
![](https://megolox.ru/gitResources/ExchangeRates.png)

## Exchange Values

100 рублей в долларах      | 100 долларов в рублях                
:-------------------------:|:-------------------------:|
![](https://megolox.ru/gitResources/ExchangeValues.png) | ![](https://megolox.ru/gitResources/ExchangeValues2.png)

# TeamCity Google Tag Manager Plugin
[![official JetBrains project](https://jb.gg/badges/official.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub) [![Build status](https://teamcity.jetbrains.com/guestAuth/app/rest/builds/buildType:(id:TeamCityPluginsByJetBrains_TeamcityGoogleTagManagerPlugin_Build)/statusIcon.svg)](https://teamcity.jetbrains.com/viewType.html?buildTypeId=TeamCityPluginsByJetBrains_TeamcityGoogleTagManagerPlugin_Build)

The simple plugin allowing to add Google Tag Manager to the TeamCity UI pages. 
The plugin is available for TeamCity versions starting from 2017.2.2

Download [latest plugin binary for 2019.1+](https://teamcity.jetbrains.com/guestAuth/app/rest/builds/buildType:TeamCityPluginsByJetBrains_TeamcityGoogleTagManagerPlugin_Build,status:SUCCESS/artifacts/content/google-tag-manager.zip)
Download [plugin for pre-2019.1 version](https://teamcity.jetbrains.com/guestAuth/app/rest/builds/branch:2018.x,buildType:TeamCityPluginsByJetBrains_TeamcityGoogleTagManagerPlugin_Build,status:SUCCESS/artifacts/content/google-tag-manager.zip)

## Configuration
 * Put the following configuration in `<TeamCity Data Directory>/config/googleTagManager/settings.xml` file 
 replacing {id} with an actual GTM container id.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<google-tag-manager container-id="{id}"/>
```
* The plugin can be disabled using internal property `teamcity.plugin.googleTagManager.enabled=false`

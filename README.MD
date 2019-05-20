# TeamCity Google Tag Manager Plugin
[![Build status](https://teamcity.jetbrains.com/guestAuth/app/rest/builds/buildType:(id:TeamCityPluginsByJetBrains_TeamcityGoogleTagManagerPlugin_Build)/statusIcon.svg)](https://teamcity.jetbrains.com/viewType.html?buildTypeId=TeamCityPluginsByJetBrains_TeamcityGoogleTagManagerPlugin_Build)

The simple plugin allowing to add Google Tag Manager to the TeamCity UI pages. 
The plugin is compatible with TeamCity starting from 2017.2.2

Download [latest plugin binary](https://teamcity.jetbrains.com/guestAuth/repository/download/TeamCityPluginsByJetBrains_TeamcityGoogleTagManagerPlugin_Build/.lastFinished/google-tag-manager.zip)

## Configuration
 * Put the following configuration in `<TeamCity Data Directory>/config/googleTagManager/settings.xml` file 
 replacing {id} with an actual GTM container id.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<google-tag-manager container-id="{id}"/>
```
* The plugin can be disabled using internal property `teamcity.plugin.googleTagManager.enabled=false`

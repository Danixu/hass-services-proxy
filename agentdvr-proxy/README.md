# Home Assistant Add-on: AgentDVR Proxy

⚠️ This addon does not contain AgentDVR ⚠️

This addon acts as a proxy to an external running AgentDVR instance. 
The sole purpose of this addon is to add a AgentDVR icon to the sidebar of Home Assistant which will open the frontend of an external running AgentDVR instance.

## Options

- `server` (required): this should be the local URL on which the AgentDVR frontend is running, e.g. `http://192.168.2.43:8080`. Make sure there is no trailing slash!
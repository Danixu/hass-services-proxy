# Home Assistant Add-on: OpenWisp Proxy

⚠️ This addon does not contain the OpenWisp service ⚠️

This addon acts as a proxy to an external running OpenWisp instance. 
The sole purpose of this addon is to add a OpenWisp icon to the sidebar of Home Assistant which will open the frontend of an external running OpenWisp instance.

## Options

- `server` (required): This should be the local URL on which the OpenWisp frontend is running, e.g. `https://192.168.2.43`. Make sure there is no trailing slash!
- `domain` (required): The configured domain of the OpenWisp instance, e.g. `openwisp.local`.
- `verify_ssl` (optional): Sets the server SSL verification. On a server with a self-signed certificate, this must be set to off.
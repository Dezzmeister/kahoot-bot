var prefs = Components.classes["@mozilla.org/preferences-service;1"].getService(Components.interfaces.nsIPrefBranch);

prefs.setIntPref("network.proxy.type", 1);
prefs.setCharPref("network.proxy.http", arguments[0]);
prefs.setIntPref("network.proxy.http_port", arguments[1]);
prefs.setCharPref("network.proxy.ssl", arguments[0]);
prefs.setIntPref("network.proxy.ssl_port", arguments[1]);
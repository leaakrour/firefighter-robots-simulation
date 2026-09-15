module app.data {
	exports org.app.data.robots;
	exports org.app.data;
	exports org.app.io;
	exports org.app.events;
	exports org.app.events.builtin;
	exports org.app.exceptions;
	exports org.app.ai;

	requires transitive java.desktop;
	requires gui;
}
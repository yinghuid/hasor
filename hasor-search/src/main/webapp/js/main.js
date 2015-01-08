require([
         'lib/order!lib/console',
         'lib/order!jquery',
         'lib/order!lib/jquery.cookie',
         'lib/order!lib/jquery.form',
         'lib/order!lib/jquery.jstree',
         'lib/order!lib/jquery.sammy',
         'lib/order!lib/jquery.timeago',
         'lib/order!lib/jquery.ajaxfileupload',
         'lib/order!lib/jquery.blockUI',
         'lib/order!lib/highlight',
         'lib/order!lib/linker',
         'lib/order!lib/ZeroClipboard',
         'lib/order!lib/d3',
         'lib/order!lib/chosen',
         'lib/order!scripts/app',

         'lib/order!scripts/analysis',
         'lib/order!scripts/cloud',
         'lib/order!scripts/cores',
         'lib/order!scripts/documents',
         'lib/order!scripts/dataimport',
         'lib/order!scripts/dashboard',
         'lib/order!scripts/files',
         'lib/order!scripts/index',
         'lib/order!scripts/java-properties',
         'lib/order!scripts/logging',
         'lib/order!scripts/ping',
         'lib/order!scripts/plugins',
         'lib/order!scripts/query',
         'lib/order!scripts/replication',
         'lib/order!scripts/schema-browser',
         'lib/order!scripts/threads'
], function($) {
	app.run();
});
module.exports = function(grunt) {
	grunt.initConfig({
		uglify: {
			build: {
				src: [
                    'www/js/config.js',
                    'www/js/app.js',
                    'www/js/menu.js',
                    'www/js/twitter.js',
                    'www/js/speakers.js',
                    'www/js/sessions.js',
                    'www/js/sessions-service.js',
                    'www/js/map.js'
                ],
				dest: 'www/app.min.js'
			}
		},
		
		processhtml: {
			prod: {
				files: {
					'www/index_prod.html': ['www/index.html']
				}
			}
		}
	});
	
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-processhtml');
	
	grunt.registerTask('default', ['uglify', 'processhtml']);
}

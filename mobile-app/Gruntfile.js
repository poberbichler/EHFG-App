module.exports = function(grunt) {
	grunt.initConfig({
		uglify: {
			build: {
				src: 'js/*.js',
				dest: 'app.min.js'
			}
		},
		
		processhtml: {
			prod: {
				files: {
					'index_prod.html': ['index.html']
				}
			}
		}
	});
	
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-processhtml');
	
	grunt.registerTask('default', ['uglify', 'processhtml']);
}

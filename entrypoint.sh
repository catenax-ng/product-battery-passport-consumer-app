!#/bin/sh
 
ROOT_DIR=/usr/share/nginx/html
 
echo "Replacing environment constants in JS"
 
for file in $ROOT_DIR/js/app.*.js* $ROOT_DIR/index.html;
do
    echo "Processing $file ...";
    sed -i 's|VUE_APP_CLIENT_ID|'${VUE_APP_CLIENT_ID}'|g' $file
done
 
exec "$@"

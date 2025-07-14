set -e

sleep 5

mc alias set local http://minio:9000 minioadmin minioadmin

mc mb local/image --ignore-existing

mc policy set public local/image

echo "✅ MinIO setup complete. Bucket 'image' is ready."
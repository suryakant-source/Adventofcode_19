const CACHE_NAME = 'bbt-v1';
const ASSETS_TO_CACHE = [
    '/',
    '/index.html',
    '/about.html',
    '/products.html',
    '/more-info.html',
    '/contact.html',
    '/css/styles.css',
    '/js/main.js',
    'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css',
    'https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&family=Open+Sans:wght@400;600&display=swap'
];

// Install Event
self.addEventListener('install', event => {
    event.waitUntil(
        caches.open(CACHE_NAME)
            .then(cache => {
                console.log('Opened cache');
                return cache.addAll(ASSETS_TO_CACHE);
            })
    );
});

// Activate Event
self.addEventListener('activate', event => {
    event.waitUntil(
        caches.keys().then(cacheNames => {
            return Promise.all(
                cacheNames.map(cache => {
                    if (cache !== CACHE_NAME) {
                        console.log('Clearing old cache');
                        return caches.delete(cache);
                    }
                })
            );
        })
    );
});

// Fetch Event (Stale-While-Revalidate Strategy)
self.addEventListener('fetch', event => {
    event.respondWith(
        caches.match(event.request)
            .then(response => {
                const fetchPromise = fetch(event.request).then(networkResponse => {
                    caches.open(CACHE_NAME).then(cache => {
                        cache.put(event.request, networkResponse.clone());
                    });
                    return networkResponse;
                });
                return response || fetchPromise;
            }).catch(() => {
                // Potential fallback for offline images or pages
                if (event.request.mode === 'navigate') {
                    return caches.match('/index.html');
                }
            })
    );
});

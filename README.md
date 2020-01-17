# quiz-note-api

## API一覧

| API名 | メソッド | URI | 認証必須 |
----|----|----|----
| ヘルスチェックAPI						| GET		| /api/v1/status							| - |
| アカウント登録API						| POST		| /api/v1/account							| - |
| アカウント取得API						| GET		| /api/v1/account/{loginId}					| - |
| アカウント更新API						| POST		| /api/v1/user/{loginId}/account/profile	| ○ |
| アカウント画像更新API					| POST		| /api/v1/user/{loginId}/account/image		| ○ |
| アカウントブロックAPI					| POST		| /api/v1/account/{loginId}/block			| - |
| アカウント通報API						| POST		| /api/v1/account/{loginId}/report			| - |
| アクティビティ取得API					| GET		| /api/v1/user/{loginId}/me					| ○ |
| フォローワーアクティビティ取得API		| GET		| /api/v1/user/{loginId}/following			| ○ |
| ログインAPI							| POST		| /api/v1/login								| - |
| ログインチェックAPI					| GET		| /api/v1/check/login						| - |
| ログアウトAPI							| POST		| /api/v1/logout							| ○ |
| FacebookログインAPI					| GET		| /api/v1/login/facebook					| - |
| お問合せ登録API						| POST		| /api/v1/contact							| - |
| フォロー取得API						| GET		| /api/v1/account/{loginId}/follow			| - |
| フォローワー取得API					| GET		| /api/v1/account/{loginId}/follower		| - |
| フォロー登録API						| POST		| /api/v1/user/{loginId}/follow				| ○ |
| フォロー解除API						| POST		| /api/v1/user/{loginId}/unfollow			| ○ |
| クイズ一覧取得API						| GET		| /api/v1/user/{loginId}/quiz				| ○※1 |
| クイズ登録API							| POST		| /api/v1/user/{loginId}/quiz				| ○ |
| クイズ更新API							| PUT		| /api/v1/user/{loginId}/quiz				| ○ |
| クイズ削除API							| DELETE	| /api/v1/user/{loginId}/quiz				| ○ |
| 問読み登録API							| POST		| /api/v1/user/{loginId}/quiz/{cd}/sound	| ○ |
| 通報API								| POST		| /api/v1/quiz/{cd}/report					| - |
| いいねAPI								| POST		| /api/v1/user/{loginId}/quiz/{cd}/like		| ○ |
| いいね解除API							| POST		| /api/v1/user/{loginId}/quiz/{cd}/dislike	| ○ |

※1 認証不要に変更する可能性あり

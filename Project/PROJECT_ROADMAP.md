# Project Roadmap & Ready-to-use Snippets

This file gathers the quick roadmap and copy-paste-ready Java snippets you can use to implement everything your teacher expects. Place this file at the project root and use the snippets in your `app/src/main/java/...` files and layouts.

## 1. Basic App Structure

- Two Activities: `HomeActivity` (list) and `DetailsActivity` (details screen).
- Navigate using Intents.
- Keep layouts simple (ConstraintLayout or LinearLayout).

Intent example (from HomeActivity to DetailsActivity):

```java
Intent intent = new Intent(this, DetailsActivity.class);
intent.putExtra("item_id", id);
startActivity(intent);
```

## 2. UI & Event Handling

- Use standard Views: `Button`, `TextView`, `EditText`.
- Use `setOnClickListener` for events.
- Use `Toast` to show quick feedback.

Toast example:

```java
Toast.makeText(this, "Action performed", Toast.LENGTH_SHORT).show();
```

Confirm dialog example (AlertDialog):

```java
new AlertDialog.Builder(this)
    .setTitle("Confirm")
    .setMessage("Are you sure?")
    .setPositiveButton("Yes", (dialog, which) -> { /* action */ })
    .setNegativeButton("No", null)
    .show();
```

## 3. Complex Components — RecyclerView + CardView

Adapter + ViewHolder skeleton (Java):

```java
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private final List<Item> items;
    private final Context context;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item it = items.get(position);
        holder.title.setText(it.title);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("item_id", it.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
        }
    }
}
```

Create `res/layout/item_card.xml` with a `CardView` wrapping simple `TextView`s.

## 4. Local Data Storage

SharedPreferences (quick settings / small key-values):

```java
SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
prefs.edit().putBoolean("seen_intro", true).apply();
boolean seen = prefs.getBoolean("seen_intro", false);
```

SQLiteOpenHelper skeleton and prepopulate sample data:

```java
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "app.db";
    private static final int DB_VER = 1;

    public DBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE items(id INTEGER PRIMARY KEY, title TEXT, body TEXT);");
        // Prepopulate
        ContentValues cv = new ContentValues();
        cv.put("title", "Sample 1");
        cv.put("body", "This is prepopulated.");
        db.insert("items", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS items");
        onCreate(db);
    }

    // Helper to read items (simple example)
    public Cursor getAllItems() {
        return getReadableDatabase().query("items", null, null, null, null, null, null);
    }
}
```

Note: prepopulating in `onCreate` with static data gives you ready content without input flows.

## 5. Remote Data Access (Optional / Mocked)

Retrofit placeholder (use assets/local JSON for fast delivery):

```java
public interface ApiService {
    @GET("/placeholder")
    Call<List<Item>> getItems();
}

// But for the fastest path, keep a JSON sample in app/src/main/assets/items.json and read it like:
// InputStream is = context.getAssets().open("items.json"); ... parse with Gson
```

Firebase note: add Firebase SDK only if required. Otherwise leave code commented and note that Firebase read/write is ready to be added.

## 6. Fastest Path (Recommended for quick delivery)

- Mock data locally: assets JSON or prepopulated SQLite. No network required.
- Implement UI components and event flows using the snippets above.
- Add simple Toasts and Dialogs to demonstrate interactivity.

## Files you can add next (suggested skeletons)

- `app/src/main/java/com/example/project/HomeActivity.java` — shows RecyclerView and loads mock data.
- `app/src/main/java/com/example/project/DetailsActivity.java` — shows details for selected item.
- `app/src/main/java/com/example/project/ItemAdapter.java` — RecyclerView adapter.
- `app/src/main/java/com/example/project/DBHelper.java` — SQLiteOpenHelper skeleton.
- `app/src/main/res/layout/activity_home.xml` — RecyclerView container.
- `app/src/main/res/layout/activity_details.xml` — TextViews showing details.
- `app/src/main/assets/items.json` — mock items

## Minimal contract (what to implement with these snippets)

- Inputs: none (mock/local data), or simple Intent extras for details screen.
- Outputs: UI screens, toasts, dialogs, and stored local data.
- Error modes: no network required; DB read failure -> show Toast and empty state.

## Edge cases to cover quickly

- Empty list: show placeholder TextView.
- DB failure: catch exceptions and fallback to assets JSON.
- Null intent extras: show default message in details screen.

## Quick next steps (if you want me to continue)

- I can create the Java skeleton files (HomeActivity, DetailsActivity, ItemAdapter, DBHelper) and minimal layouts, plus an `items.json` asset and update `AndroidManifest.xml` if you want — say "Please scaffold skeletons" and I'll implement them.

---

Keep this file as a single reference. Copy snippets into proper `.java` and `.xml` files when ready.

## Project example: "My Notes App"

Concept: A simple note-taking app where users can add, view, edit, and delete notes. This example maps directly to the topics your teacher expects and is fast to implement.

Why choose this app:

- Covers Activities + Intents (navigation between list and add/edit screens).
- Uses standard Views (Button, EditText, TextView) and event handling with `setOnClickListener`.
- Demonstrates `Toast` notifications and `AlertDialog` confirmations.
- Uses `RecyclerView` + `CardView` for the list of notes and Adapter/ViewHolder pattern.
- Stores data locally with `SQLiteOpenHelper` (or `SharedPreferences` for a simpler option).
- Optionally mock remote access with a local JSON in `assets/` or add a Firebase skeleton.

Feature mapping table (quick reference):

- Add/Edit/Delete Notes — Activities, Intents, Buttons, EditText
- List Notes — RecyclerView + CardView (Adapter + ViewHolder)
- Toast Notifications — show on save/delete
- Confirmation Dialog — AlertDialog when deleting a note
- Local Storage — SQLite (recommended) or SharedPreferences
- Optional REST API — mock via `assets/items.json` and parse with Gson
- Optional Firebase — add SDK later; keep code commented until required

Minimal screens and files to implement next (suggested skeleton):

- `app/src/main/java/com/example/project/HomeActivity.java` — shows RecyclerView and a FAB to add notes.
- `app/src/main/java/com/example/project/NoteEditActivity.java` — add/edit note UI with `EditText` and save/delete buttons.
- `app/src/main/java/com/example/project/NoteAdapter.java` — RecyclerView adapter for notes.
- `app/src/main/java/com/example/project/DBHelper.java` — `SQLiteOpenHelper` to store notes and prepopulate sample notes.
- `app/src/main/res/layout/activity_home.xml` — RecyclerView container and FAB.
- `app/src/main/res/layout/activity_note_edit.xml` — EditText(s) and action Buttons.
- `app/src/main/res/layout/item_note.xml` — CardView for individual notes.
- `app/src/main/assets/notes_sample.json` — optional mock data.

Quick implementation notes (fast delivery):

- Keep two Activities only: list (Home) and add/edit (NoteEdit).
- Prepopulate `DBHelper.onCreate()` with 2–3 sample notes so app is demonstrable without input flows.
- Show `Toast` on successful save and delete; show `AlertDialog` when user tries to delete.
- For sorting or settings, add a simple options menu in `HomeActivity`.

Next steps I can take for you (pick one or more):

1. Scaffold the full skeleton (Java classes + layouts + assets + manifest updates) so you can open the project and run immediately.
2. Implement the SQLite-backed CRUD flow and wire RecyclerView to live DB data.
3. Add a mocked Retrofit/JSON asset example or a commented Firebase integration.
4. Add minimal unit tests for `DBHelper` and `NoteAdapter` and a short `README.md` with run instructions.

Tell me which of the next steps you want me to do and I'll implement them (I can scaffold everything automatically).

package org.wordpress.android.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.automattic.android.tracks.TracksClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.wordpress.android.util.AppLog;

import java.util.Map;
import java.util.UUID;

public class AnalyticsTrackerNosara implements AnalyticsTracker.Tracker {

    private static final String JETPACK_USER = "jetpack_user";
    private static final String NUMBER_OF_BLOGS = "number_of_blogs";
    private static final String TRACKS_ANON_ID = "nosara_tracks_anon_id";

    private static final String EVENTS_PREFIX = "wpandroid_";

    private String mWpcomUserName = null;
    private String mAnonID = null; // do not access this variable directly. Use methods.

    private TracksClient mNosaraClient;
    private Context mContext;

    public AnalyticsTrackerNosara(Context context) {
        if (null == context) {
            mNosaraClient = null;
            return;
        }
        mContext = context;
        mNosaraClient = TracksClient.getClient(context);
    }

    @Override
    public void track(AnalyticsTracker.Stat stat) {
        track(stat, null);
    }

    @Override
    public void track(AnalyticsTracker.Stat stat, Map<String, ?> properties) {
        if (mNosaraClient == null) {
            return;
        }

        String eventName;

        switch (stat) {
            case APPLICATION_STARTED:
                eventName = "application_started";
                break;
            case APPLICATION_OPENED:
                eventName = "application_opened";
                break;
            case APPLICATION_CLOSED:
                eventName = "application_closed";
                break;
            case THEMES_ACCESSED_THEMES_BROWSER:
                eventName = "themes_accessed_theme_browser";
                break;
            case THEMES_CHANGED_THEME:
                eventName = "themes_changed_theme";
                break;
            case THEMES_PREVIEWED_SITE:
                eventName = "themes_previewed_theme_for_site";
                break;
            case READER_ACCESSED:
                eventName = "reader_accessed";
                break;
            case READER_OPENED_ARTICLE:
                eventName = "reader_opened_article";
                break;
            case READER_LIKED_ARTICLE:
                eventName = "reader_liked_article";
                break;
            case READER_REBLOGGED_ARTICLE:
                eventName = "reader_reblogged_article";
                break;
            case READER_INFINITE_SCROLL:
                eventName = "reader_infinite_scroll_performed";
                break;
            case READER_FOLLOWED_READER_TAG:
                eventName = "reader_followed_reader_tag";
                break;
            case READER_UNFOLLOWED_READER_TAG:
                eventName = "reader_unfollowed_reader_tag";
                break;
            case READER_LOADED_TAG:
                eventName = "reader_loaded_tag";
                break;
            case READER_LOADED_FRESHLY_PRESSED:
                eventName = "reader_loaded_freshly_pressed";
                break;
            case READER_COMMENTED_ON_ARTICLE:
                eventName = "reader_commented_on_article";
                break;
            case READER_FOLLOWED_SITE:
                eventName = "reader_followed_site";
                break;
            case READER_BLOCKED_BLOG:
                eventName = "reader_blocked_blog";
                break;
            case READER_BLOG_PREVIEW:
                eventName = "reader_blog_preview";
                break;
            case READER_TAG_PREVIEW:
                eventName = "reader_tag_preview";
                break;
            case EDITOR_CREATED_POST:
                eventName = "editor_created_post";
                break;
            case EDITOR_SAVED_DRAFT:
                eventName = "editor_saved_draft";
                break;
            case EDITOR_CLOSED_POST:
                eventName = "editor_closed";
                break;
            case EDITOR_ADDED_PHOTO_VIA_LOCAL_LIBRARY:
                eventName = "editor_added_photo_via_local_library";
                break;
            case EDITOR_ADDED_PHOTO_VIA_WP_MEDIA_LIBRARY:
                eventName = "editor_added_photo_via_wp_media_library";
                break;
            case EDITOR_PUBLISHED_POST:
                eventName = "editor_published_post";
                break;
            case EDITOR_UPDATED_POST:
                eventName = "editor_update_post";
                break;
            case EDITOR_SCHEDULED_POST:
                eventName = "editor_scheduled_post";
                break;
            case EDITOR_PUBLISHED_POST_WITH_PHOTO:
                eventName = "editor_published_post_with_photos";
                break;
            case EDITOR_PUBLISHED_POST_WITH_VIDEO:
                eventName = "editor_published_post_with_videos";
                break;
            case EDITOR_PUBLISHED_POST_WITH_CATEGORIES:
                eventName = "editor_published_post_with_categories";
                break;
            case EDITOR_PUBLISHED_POST_WITH_TAGS:
                eventName = "editor_published_post_with_tags";
                break;
            case EDITOR_TAPPED_BLOCKQUOTE:
                eventName = "editor_tapped_blockquote_button";
                break;
            case EDITOR_TAPPED_BOLD:
                eventName = "editor_tapped_bold_button";
                break;
            case EDITOR_TAPPED_IMAGE:
                eventName = "editor_tapped_image_button";
                break;
            case EDITOR_TAPPED_ITALIC:
                eventName = "editor_tapped_italic_button";
                break;
            case EDITOR_TAPPED_LINK:
                eventName = "editor_tapped_link_button";
                break;
            case EDITOR_TAPPED_MORE:
                eventName = "editor_tapped_more_button";
                break;
            case EDITOR_TAPPED_STRIKETHROUGH:
                eventName = "editor_tapped_strikethrough_button";
                break;
            case EDITOR_TAPPED_UNDERLINE:
                eventName = "editor_tapped_underline_button";
                break;
            case NOTIFICATIONS_ACCESSED:
                eventName = "notifications_accessed";
                break;
            case NOTIFICATIONS_OPENED_NOTIFICATION_DETAILS:
                eventName = "notifications_opened_notification_details";
                break;
            case NOTIFICATION_APPROVED:
                eventName = "notifications_approved";
                break;
            case NOTIFICATION_UNAPPROVED:
                eventName = "notifications_unapproved";
                break;
            case NOTIFICATION_REPLIED_TO:
                eventName = "notifications_replied_to";
                break;
            case NOTIFICATION_TRASHED:
                eventName = "notifications_trashed";
                break;
            case NOTIFICATION_FLAGGED_AS_SPAM:
                eventName = "notifications_flagged_as_spam";
                break;
            case NOTIFICATION_LIKED:
                eventName = "notifications_liked_comment";
                break;
            case NOTIFICATION_UNLIKED:
                eventName = "notifications_unliked_comment";
                break;
            case OPENED_POSTS:
                eventName = "site_menu_opened_posts";
                break;
            case OPENED_PAGES:
                eventName = "site_menu_opened_pages";
                break;
            case OPENED_COMMENTS:
                eventName = "site_menu_opened_comments";
                break;
            case OPENED_VIEW_SITE:
                eventName = "site_menu_opened_view_site";
                break;
            case OPENED_VIEW_ADMIN:
                eventName = "site_menu_opened_view_admin";
                break;
            case OPENED_MEDIA_LIBRARY:
                eventName = "site_menu_opened_media_library";
                break;
            case OPENED_SITE_SETTINGS:
                eventName = "site_menu_opened_site_settings";
                break;
            case OPENED_MY_PROFILE:
                eventName = "me_opened_my_profile";
                break;
            case OPENED_ACCOUNT_SETTINGS:
                eventName = "me_opened_account_settings";
                break;
            case CREATED_ACCOUNT:
                eventName = "created_account";
                break;
            case SHARED_ITEM:
                eventName = "shared_item";
                break;
            case ADDED_SELF_HOSTED_SITE:
                eventName = "added_self_hosted_blog";
                break;
            case SIGNED_IN:
                eventName = "signed_in";
                break;
            case SIGNED_INTO_JETPACK:
                eventName = "signed_into_jetpack";
                break;
            case PERFORMED_JETPACK_SIGN_IN_FROM_STATS_SCREEN:
                eventName = "signed_into_jetpack_from_stats_screen";
                break;
            case STATS_ACCESSED:
                eventName = "stats_accessed";
                break;
            case STATS_VIEW_ALL_ACCESSED:
                eventName = "stats_view_all_accessed";
                break;
            case STATS_SINGLE_POST_ACCESSED:
                eventName = "stats_single_post_accessed";
                break;
            case STATS_OPENED_WEB_VERSION:
                eventName = "stats_opened_web_version_accessed";
                break;
            case STATS_TAPPED_BAR_CHART:
                eventName = "stats_tapped_bar_chart";
                break;
            case STATS_SCROLLED_TO_BOTTOM:
                eventName = "stats_scrolled_to_bottom";
                break;
            case STATS_SELECTED_INSTALL_JETPACK:
                eventName = "stats_selected_install_jetpack";
                break;
            case PUSH_NOTIFICATION_RECEIVED:
                eventName = "push_notification_received";
                break;
            case SUPPORT_OPENED_HELPSHIFT_SCREEN:
                eventName = "support_opened_helpshift_screen";
                break;
            case LOGIN_FAILED:
                eventName = "login_failed_login";
                break;
            case LOGIN_FAILED_TO_GUESS_XMLRPC:
                eventName = "login_failed_to_guess_xmlrpc";
                break;
            default:
                eventName = null;
                break;
        }

        if (eventName == null) {
            AppLog.w(AppLog.T.STATS, "There is NO match for the event " + stat.name() + "stat");
            return;
        }

        final String user;
        final TracksClient.NosaraUserType userType;
        if (mWpcomUserName != null) {
            user = mWpcomUserName;
            userType = TracksClient.NosaraUserType.WPCOM;
        } else {
            // This is just a security checks since the anonID is already available here.
            // refresh metadata is called on login/logout/startup and it loads/generates the anonId when necessary.
            if (getAnonID() == null) {
                user = generateNewAnonID();
            } else {
                user = getAnonID();
            }
            userType = TracksClient.NosaraUserType.ANON;
        }

        if (properties != null && properties.size() > 0) {
            JSONObject propertiesToJSON = new JSONObject(properties);
            mNosaraClient.track(EVENTS_PREFIX + eventName, propertiesToJSON, user, userType);
        } else {
            mNosaraClient.track(EVENTS_PREFIX + eventName, user, userType);
        }
    }

    private void clearAnonID() {
        mAnonID = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (preferences.contains(TRACKS_ANON_ID)) {
            final SharedPreferences.Editor editor = preferences.edit();
            editor.remove(TRACKS_ANON_ID);
            editor.commit();
        }
    }

    private String getAnonID() {
        if (mAnonID == null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            mAnonID = preferences.getString(TRACKS_ANON_ID, null);
        }
        return mAnonID;
    }

    private String generateNewAnonID() {
        String uuid = UUID.randomUUID().toString();
        String[] uuidSplitted = uuid.split("-");
        StringBuilder builder = new StringBuilder();
        for (String currentPart : uuidSplitted) {
            builder.append(currentPart);
        }
        uuid = builder.toString();
        AppLog.d(AppLog.T.STATS, "New anon ID generated: " + uuid);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TRACKS_ANON_ID, uuid);
        editor.commit();

        mAnonID = uuid;
        return uuid;
    }

    @Override
    public void endSession() {
        if (mNosaraClient == null) {
            return;
        }
        mNosaraClient.flush();
    }

    @Override
    public void refreshMetadata(boolean isUserConnected, boolean isWordPressComUser, boolean isJetpackUser,
                                int sessionCount, int numBlogs, int versionCode, String username, String email) {
        if (mNosaraClient == null) {
            return;
        }

        if (isWordPressComUser) {
            mWpcomUserName = username;
            // Re-unify the user
            if (getAnonID() != null) {
                mNosaraClient.trackAliasUser(mWpcomUserName, getAnonID());
                clearAnonID();
            }
        } else {
            // Not wpcom connected. Check if anonID is already present
            mWpcomUserName = null;
            if (getAnonID() == null) {
                generateNewAnonID();
            }
        }

        try {
            JSONObject properties = new JSONObject();
            properties.put(JETPACK_USER, isJetpackUser);
            properties.put(NUMBER_OF_BLOGS, numBlogs);
            mNosaraClient.registerUserProperties(properties);
        } catch (JSONException e) {
            AppLog.e(AppLog.T.UTILS, e);
        }
    }


    @Override
    public void clearAllData() {
        if (mNosaraClient == null) {
            return;
        }
        mNosaraClient.clearUserProperties();
        // Reset the anon ID here
        clearAnonID();
        mWpcomUserName = null;
    }

    @Override
    public void registerPushNotificationToken(String regId) {
        return;
    }
}

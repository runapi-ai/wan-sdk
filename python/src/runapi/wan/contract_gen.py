CONTRACT = {
    "animate": {
        "models": ["wan-2.2-animate-move", "wan-2.2-animate-replace"],
        "fields_by_model": {
            "wan-2.2-animate-move": {
                "output_resolution": {
                    "enum": ["480p", "580p", "720p"]
                },
                "reference_video_url": {
                    "required": True
                },
                "source_image_url": {
                    "required": True
                }
            },
            "wan-2.2-animate-replace": {
                "output_resolution": {
                    "enum": ["480p", "580p", "720p"]
                },
                "reference_video_url": {
                    "required": True
                },
                "source_image_url": {
                    "required": True
                }
            }
        }
    },
    "edit-video": {
        "models": ["wan-2.6-edit-video", "wan-2.6-flash-edit-video", "wan-2.7-edit-video"],
        "fields_by_model": {
            "wan-2.6-edit-video": {
                "duration_seconds": {
                    "type": "integer"
                },
                "output_resolution": {
                    "enum": ["720p", "1080p"]
                },
                "prompt": {
                    "required": True
                },
                "seed": {
                    "type": "integer"
                },
                "source_video_urls": {
                    "required": True
                }
            },
            "wan-2.6-flash-edit-video": {
                "duration_seconds": {
                    "type": "integer"
                },
                "prompt": {
                    "required": True
                },
                "seed": {
                    "type": "integer"
                },
                "source_video_urls": {
                    "required": True
                }
            },
            "wan-2.7-edit-video": {
                "aspect_ratio": {
                    "enum": ["16:9", "9:16", "1:1", "4:3", "3:4"]
                },
                "duration_seconds": {
                    "type": "integer"
                },
                "output_resolution": {
                    "enum": ["720p", "1080p"]
                },
                "seed": {
                    "type": "integer"
                },
                "source_video_url": {
                    "required": True
                }
            }
        },
        "rules": [{
            "when": {
                "model": "wan-2.7-edit-video"
            },
            "forbidden": ["multi_shots"]
        }]
    },
    "image-to-video": {
        "models": ["wan-2.2-a14b-image-to-video-turbo", "wan-2.5-image-to-video", "wan-2.6-flash-image-to-video", "wan-2.6-image-to-video", "wan-2.7-image-to-video"],
        "fields_by_model": {
            "wan-2.2-a14b-image-to-video-turbo": {
                "duration_seconds": {
                    "type": "integer"
                },
                "first_frame_image_url": {
                    "required": True
                },
                "output_resolution": {
                    "enum": ["480p", "720p"]
                },
                "seed": {
                    "type": "integer"
                }
            },
            "wan-2.5-image-to-video": {
                "duration_seconds": {
                    "required": True,
                    "type": "integer"
                },
                "first_frame_image_url": {
                    "required": True
                },
                "output_resolution": {
                    "enum": ["720p", "1080p"]
                },
                "seed": {
                    "type": "integer"
                }
            },
            "wan-2.6-flash-image-to-video": {
                "audio": {
                    "required": True
                },
                "duration_seconds": {
                    "enum": [5, 10, 15],
                    "type": "integer"
                },
                "first_frame_image_url": {
                    "required": True
                },
                "output_resolution": {
                    "enum": ["720p", "1080p"]
                },
                "prompt": {
                    "required": True
                }
            },
            "wan-2.6-image-to-video": {
                "duration_seconds": {
                    "type": "integer"
                },
                "first_frame_image_url": {
                    "required": True
                },
                "output_resolution": {
                    "enum": ["720p", "1080p"]
                },
                "prompt": {
                    "required": True
                }
            },
            "wan-2.7-image-to-video": {
                "duration_seconds": {
                    "type": "integer"
                },
                "output_resolution": {
                    "enum": ["720p", "1080p"]
                },
                "prompt": {
                    "required": True
                },
                "seed": {
                    "type": "integer"
                }
            }
        },
        "rules": [{
            "when": {
                "model": "wan-2.2-a14b-image-to-video-turbo"
            },
            "forbidden": ["multi_shots"]
        }, {
            "when": {
                "model": "wan-2.5-image-to-video"
            },
            "forbidden": ["multi_shots"]
        }, {
            "when": {
                "model": "wan-2.6-flash-image-to-video"
            },
            "forbidden": ["seed"]
        }, {
            "when": {
                "model": "wan-2.6-image-to-video"
            },
            "forbidden": ["seed"]
        }, {
            "when": {
                "model": "wan-2.7-image-to-video"
            },
            "forbidden": ["multi_shots"]
        }]
    },
    "speech-to-video": {
        "models": ["wan-2.2-a14b-speech-to-video-turbo"],
        "fields_by_model": {
            "wan-2.2-a14b-speech-to-video-turbo": {
                "frames_per_second": {
                    "type": "integer"
                },
                "num_frames": {
                    "type": "integer"
                },
                "num_inference_steps": {
                    "type": "integer"
                },
                "output_resolution": {
                    "enum": ["480p", "580p", "720p"]
                },
                "prompt": {
                    "required": True
                },
                "seed": {
                    "type": "integer"
                },
                "source_audio_url": {
                    "required": True
                },
                "source_image_url": {
                    "required": True
                }
            }
        }
    },
    "text-to-image": {
        "models": ["wan-2.7-image", "wan-2.7-image-pro"],
        "fields_by_model": {
            "wan-2.7-image": {
                "aspect_ratio": {
                    "enum": ["1:1", "16:9", "4:3", "21:9", "3:4", "9:16", "8:1", "1:8"]
                },
                "output_count": {
                    "type": "integer"
                },
                "output_resolution": {
                    "enum": ["1k", "2k", "4k"]
                },
                "seed": {
                    "type": "integer"
                }
            },
            "wan-2.7-image-pro": {
                "aspect_ratio": {
                    "enum": ["1:1", "16:9", "4:3", "21:9", "3:4", "9:16", "8:1", "1:8"]
                },
                "output_count": {
                    "type": "integer"
                },
                "output_resolution": {
                    "enum": ["1k", "2k", "4k"]
                },
                "seed": {
                    "type": "integer"
                }
            }
        }
    },
    "text-to-video": {
        "models": ["wan-2.2-a14b-text-to-video-turbo", "wan-2.5-text-to-video", "wan-2.6-text-to-video", "wan-2.7-r2v", "wan-2.7-text-to-video"],
        "fields_by_model": {
            "wan-2.2-a14b-text-to-video-turbo": {
                "duration_seconds": {
                    "type": "integer"
                },
                "output_resolution": {
                    "enum": ["480p", "580p", "720p"]
                },
                "seed": {
                    "type": "integer"
                }
            },
            "wan-2.5-text-to-video": {
                "duration_seconds": {
                    "type": "integer"
                },
                "output_resolution": {
                    "enum": ["720p", "1080p"]
                },
                "seed": {
                    "type": "integer"
                }
            },
            "wan-2.6-text-to-video": {
                "duration_seconds": {
                    "type": "integer"
                },
                "output_resolution": {
                    "enum": ["720p", "1080p"]
                }
            },
            "wan-2.7-r2v": {
                "aspect_ratio": {
                    "enum": ["16:9", "9:16", "1:1", "4:3", "3:4"]
                },
                "duration_seconds": {
                    "min": 2,
                    "max": 10,
                    "type": "integer"
                },
                "output_resolution": {
                    "enum": ["720p", "1080p"]
                },
                "seed": {
                    "type": "integer"
                }
            },
            "wan-2.7-text-to-video": {
                "duration_seconds": {
                    "type": "integer"
                },
                "output_resolution": {
                    "enum": ["720p", "1080p"]
                },
                "seed": {
                    "type": "integer"
                }
            }
        },
        "rules": [{
            "when": {
                "model": "wan-2.2-a14b-text-to-video-turbo"
            },
            "forbidden": ["multi_shots"]
        }, {
            "when": {
                "model": "wan-2.5-text-to-video"
            },
            "forbidden": ["multi_shots"]
        }, {
            "when": {
                "model": "wan-2.6-text-to-video"
            },
            "forbidden": ["seed"]
        }, {
            "when": {
                "model": "wan-2.7-r2v"
            },
            "forbidden": ["multi_shots"]
        }, {
            "when": {
                "model": "wan-2.7-text-to-video"
            },
            "forbidden": ["multi_shots"]
        }]
    }
}
